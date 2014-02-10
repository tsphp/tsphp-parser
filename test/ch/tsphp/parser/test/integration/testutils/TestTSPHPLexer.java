package ch.tsphp.parser.test.integration.testutils;

import ch.tsphp.parser.antlrmod.ErrorReportingTSPHPLexer;
import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

/**
 * Overwrite reportError methods in order that unit tests with lexer errors fails at the end.
 */
public class TestTSPHPLexer extends ErrorReportingTSPHPLexer
{

    private boolean isErrorReportingOn = true;
    protected final List<Exception> exceptions = new ArrayList<>();

    public TestTSPHPLexer(CharStream input) {
        super(input);
    }

    public void setErrorReporting(boolean isOn) {
        isErrorReportingOn = isOn;
    }

    @Override
    public void reportError(RecognitionException e) {
        super.reportError(e);
        exceptions.add(e);
        if (isErrorReportingOn) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<Exception> getExceptions() {
        return exceptions;
    }

    public RecognizerSharedState getState() {
        return state;
    }
}