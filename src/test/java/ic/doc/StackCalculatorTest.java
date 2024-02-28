package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class StackCalculatorTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    StackCalculator stackCalculator;
    UpdatableView observer = context.mock(UpdatableView.class);


    @Before
    public void setup() {
        stackCalculator = new StackCalculator();
    }

    @Test
    public void successfullyPushTwoIntegers() {
        try {
            stackCalculator.push('5');
            stackCalculator.push('9');
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldNotAcceptOperatorWithoutAnyInteger() {
        stackCalculator.push('+');
        Assert.assertTrue(stackCalculator.isEmpty());
    }

    @Test
    public void shouldNotAcceptOperatorWithoutTwoIntegers() {
        stackCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            oneOf(observer).updateResultField(stackCalculator);
        }});
        context.checking(new Expectations() {{
            oneOf(observer).updateError(with(any(Exception.class)));
        }});
        stackCalculator.push('2');
        stackCalculator.push('+');
        Assert.assertFalse(stackCalculator.isEmpty());
    }

    @Test
    public void acceptOperatorsAfterTwoIntegers() {
        try {
            stackCalculator.push('3');
            stackCalculator.push('7');
            stackCalculator.push('*');
            Assert.assertFalse(stackCalculator.isEmpty());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldNotAcceptInvalidOperators() {
        stackCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            exactly(2).of(observer).updateResultField(stackCalculator);
        }});
        context.checking(new Expectations() {{
            oneOf(observer).updateError(with(any(Exception.class)));
        }});
        stackCalculator.push('3');
        stackCalculator.push('7');
        stackCalculator.push('C');
        Assert.assertFalse(stackCalculator.isEmpty());
    }

    @Test
    public void calculateCorrectResult() {
        try {
            stackCalculator.push('3');
            stackCalculator.push('7');
            stackCalculator.push('*');
            stackCalculator.push('5');
            stackCalculator.push('-');
            stackCalculator.push('9');
            stackCalculator.push('+');
            stackCalculator.push('5');
            stackCalculator.push('/');
            Assert.assertEquals(5, stackCalculator.top());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void showTheTopElement() {
        try {
            stackCalculator.push('9');
            stackCalculator.push('4');
            stackCalculator.push('/');
            Assert.assertEquals(2, stackCalculator.top());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void showZeroAsTopWhenEmpty() {
        try {
            Assert.assertEquals(0, stackCalculator.top());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void clearStack() {
        try {
            stackCalculator.push('3');
            Assert.assertFalse(stackCalculator.isEmpty());
            stackCalculator.clear();
            Assert.assertTrue(stackCalculator.isEmpty());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void observerUpdatedOnPush() {
        stackCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            oneOf(observer).updateResultField(stackCalculator);
        }});
        context.checking(new Expectations() {{
            exactly(0).of(observer).updateError(with(any(Exception.class)));
        }});
        stackCalculator.push('1');
    }

    @Test
    public void observerErrorUpdatedOnError() {
        stackCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            oneOf(observer).updateError(with(any(Exception.class)));
        }});
        stackCalculator.push('/');
    }
}
