package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ReversePolishCalculatorTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    ReversePolishCalculator reversePolishCalculator;
    UpdatableView observer = context.mock(UpdatableView.class);


    @Before
    public void setup() {
        reversePolishCalculator = new ReversePolishCalculator();
    }

    @Test
    public void successfullyPushTwoIntegers() {
        try {
            reversePolishCalculator.push('5');
            reversePolishCalculator.push('9');
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldNotAcceptOperatorWithoutAnyInteger() {
        reversePolishCalculator.push('+');
        Assert.assertTrue(reversePolishCalculator.isEmpty());
    }

    @Test
    public void shouldNotAcceptOperatorWithoutTwoIntegers() {
        reversePolishCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            oneOf(observer).updateResultField(reversePolishCalculator);
        }});
        context.checking(new Expectations() {{
            oneOf(observer).updateError(with(any(Exception.class)));
        }});
        reversePolishCalculator.push('2');
        reversePolishCalculator.push('+');
        Assert.assertFalse(reversePolishCalculator.isEmpty());
    }

    @Test
    public void acceptOperatorsAfterTwoIntegers() {
        try {
            reversePolishCalculator.push('3');
            reversePolishCalculator.push('7');
            reversePolishCalculator.push('*');
            Assert.assertFalse(reversePolishCalculator.isEmpty());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void shouldNotAcceptInvalidOperators() {
        reversePolishCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            exactly(2).of(observer).updateResultField(reversePolishCalculator);
        }});
        context.checking(new Expectations() {{
            oneOf(observer).updateError(with(any(Exception.class)));
        }});
        reversePolishCalculator.push('3');
        reversePolishCalculator.push('7');
        reversePolishCalculator.push('C');
        Assert.assertFalse(reversePolishCalculator.isEmpty());
    }

    @Test
    public void calculateCorrectResult() {
        try {
            reversePolishCalculator.push('3');
            reversePolishCalculator.push('7');
            reversePolishCalculator.push('*');
            reversePolishCalculator.push('5');
            reversePolishCalculator.push('-');
            reversePolishCalculator.push('9');
            reversePolishCalculator.push('+');
            reversePolishCalculator.push('5');
            reversePolishCalculator.push('/');
            Assert.assertEquals(5, reversePolishCalculator.showResult());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void showTheTopElement() {
        try {
            reversePolishCalculator.push('9');
            reversePolishCalculator.push('4');
            reversePolishCalculator.push('/');
            Assert.assertEquals(2, reversePolishCalculator.showResult());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void showZeroAsTopWhenEmpty() {
        try {
            Assert.assertEquals(0, reversePolishCalculator.showResult());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void clearStack() {
        try {
            reversePolishCalculator.push('3');
            Assert.assertFalse(reversePolishCalculator.isEmpty());
            reversePolishCalculator.clear();
            Assert.assertTrue(reversePolishCalculator.isEmpty());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void observerUpdatedOnPush() {
        reversePolishCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            oneOf(observer).updateResultField(reversePolishCalculator);
        }});
        context.checking(new Expectations() {{
            exactly(0).of(observer).updateError(with(any(Exception.class)));
        }});
        reversePolishCalculator.push('1');
    }

    @Test
    public void observerErrorUpdatedOnError() {
        reversePolishCalculator.addObserver(observer);
        context.checking(new Expectations() {{
            oneOf(observer).updateError(with(any(Exception.class)));
        }});
        reversePolishCalculator.push('/');
    }
}
