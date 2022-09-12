// Generated from D:/git/Alax/syntax/src/main/antlr4/language\LanguageLexer.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		THIS=1, SUPER=2, OUTER=3, BOOLEAN_LITERAL=4, STRING_LITERAL=5, CHARACTER_LITERAL=6, 
		FLOAT_LITERAL=7, INTEGER_LITERAL=8, UPPERCASE_NAME=9, LOWERCASE_NAME=10, 
		VALUE_TYPE_NAME=11, PACKAGE_NAME=12, ASSIGNMENT=13, STATEMENT_END=14, 
		WS=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"TRUE", "FALSE", "QUOTE", "APOSTROPHE", "DOT", "SEMI_COLON", "COLON", 
			"COMMA", "OPEN_CURLY", "CLOSE_CURLY", "OPEN_SQUARE", "CLOSE_SQUARE", 
			"OPEN_TRIANGLE", "CLOSE_TRIANGLE", "OPEN_BRACKET", "CLOSE_BRACKET", "MINUS", 
			"PLUS", "AND", "PIPE", "UNDERSCORE", "CHARACTER", "DIGITS", "DIGIT", 
			"UPPERCASE_LETTER", "LOWERCASE_LETTER", "THIS", "SUPER", "OUTER", "BOOLEAN_LITERAL", 
			"STRING_LITERAL", "CHARACTER_LITERAL", "FLOAT_LITERAL", "INTEGER_LITERAL", 
			"UPPERCASE_NAME", "LOWERCASE_NAME", "VALUE_TYPE_NAME", "PACKAGE_NAME", 
			"ASSIGNMENT", "STATEMENT_END", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'this'", "'super'", "'outer'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "THIS", "SUPER", "OUTER", "BOOLEAN_LITERAL", "STRING_LITERAL", 
			"CHARACTER_LITERAL", "FLOAT_LITERAL", "INTEGER_LITERAL", "UPPERCASE_NAME", 
			"LOWERCASE_NAME", "VALUE_TYPE_NAME", "PACKAGE_NAME", "ASSIGNMENT", "STATEMENT_END", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LanguageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LanguageLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000f\u00ef\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d"+
		"\u0002\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!"+
		"\u0007!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002"+
		"&\u0007&\u0002\'\u0007\'\u0002(\u0007(\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015"+
		"\u0089\b\u0015\u0001\u0016\u0004\u0016\u008c\b\u0016\u000b\u0016\f\u0016"+
		"\u008d\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001d\u0001\u001d\u0003\u001d\u00a9\b\u001d\u0001\u001e\u0001\u001e\u0005"+
		"\u001e\u00ad\b\u001e\n\u001e\f\u001e\u00b0\t\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0003 \u00b9\b"+
		" \u0001 \u0001 \u0001 \u0001 \u0001!\u0003!\u00c0\b!\u0001!\u0001!\u0001"+
		"\"\u0001\"\u0001\"\u0001\"\u0001\"\u0005\"\u00c9\b\"\n\"\f\"\u00cc\t\""+
		"\u0001#\u0001#\u0001#\u0001#\u0001#\u0005#\u00d3\b#\n#\f#\u00d6\t#\u0001"+
		"$\u0001$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001%\u0005%\u00e0\b%\n%"+
		"\f%\u00e3\t%\u0001&\u0001&\u0001\'\u0001\'\u0001(\u0004(\u00ea\b(\u000b"+
		"(\f(\u00eb\u0001(\u0001(\u0000\u0000)\u0001\u0000\u0003\u0000\u0005\u0000"+
		"\u0007\u0000\t\u0000\u000b\u0000\r\u0000\u000f\u0000\u0011\u0000\u0013"+
		"\u0000\u0015\u0000\u0017\u0000\u0019\u0000\u001b\u0000\u001d\u0000\u001f"+
		"\u0000!\u0000#\u0000%\u0000\'\u0000)\u0000+\u0000-\u0000/\u00001\u0000"+
		"3\u00005\u00017\u00029\u0003;\u0004=\u0005?\u0006A\u0007C\bE\tG\nI\u000b"+
		"K\fM\rO\u000eQ\u000f\u0001\u0000\u0004\u0001\u000009\u0001\u0000AZ\u0001"+
		"\u0000az\u0003\u0000\t\n\r\r  \u00e6\u00005\u0001\u0000\u0000\u0000\u0000"+
		"7\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001"+
		"\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000"+
		"\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000"+
		"E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001"+
		"\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000"+
		"\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0001"+
		"S\u0001\u0000\u0000\u0000\u0003X\u0001\u0000\u0000\u0000\u0005^\u0001"+
		"\u0000\u0000\u0000\u0007`\u0001\u0000\u0000\u0000\tb\u0001\u0000\u0000"+
		"\u0000\u000bd\u0001\u0000\u0000\u0000\rf\u0001\u0000\u0000\u0000\u000f"+
		"h\u0001\u0000\u0000\u0000\u0011j\u0001\u0000\u0000\u0000\u0013l\u0001"+
		"\u0000\u0000\u0000\u0015n\u0001\u0000\u0000\u0000\u0017p\u0001\u0000\u0000"+
		"\u0000\u0019r\u0001\u0000\u0000\u0000\u001bt\u0001\u0000\u0000\u0000\u001d"+
		"v\u0001\u0000\u0000\u0000\u001fx\u0001\u0000\u0000\u0000!z\u0001\u0000"+
		"\u0000\u0000#|\u0001\u0000\u0000\u0000%~\u0001\u0000\u0000\u0000\'\u0080"+
		"\u0001\u0000\u0000\u0000)\u0082\u0001\u0000\u0000\u0000+\u0088\u0001\u0000"+
		"\u0000\u0000-\u008b\u0001\u0000\u0000\u0000/\u008f\u0001\u0000\u0000\u0000"+
		"1\u0091\u0001\u0000\u0000\u00003\u0093\u0001\u0000\u0000\u00005\u0095"+
		"\u0001\u0000\u0000\u00007\u009a\u0001\u0000\u0000\u00009\u00a0\u0001\u0000"+
		"\u0000\u0000;\u00a8\u0001\u0000\u0000\u0000=\u00aa\u0001\u0000\u0000\u0000"+
		"?\u00b3\u0001\u0000\u0000\u0000A\u00b8\u0001\u0000\u0000\u0000C\u00bf"+
		"\u0001\u0000\u0000\u0000E\u00c3\u0001\u0000\u0000\u0000G\u00cd\u0001\u0000"+
		"\u0000\u0000I\u00d7\u0001\u0000\u0000\u0000K\u00db\u0001\u0000\u0000\u0000"+
		"M\u00e4\u0001\u0000\u0000\u0000O\u00e6\u0001\u0000\u0000\u0000Q\u00e9"+
		"\u0001\u0000\u0000\u0000ST\u0005t\u0000\u0000TU\u0005r\u0000\u0000UV\u0005"+
		"u\u0000\u0000VW\u0005e\u0000\u0000W\u0002\u0001\u0000\u0000\u0000XY\u0005"+
		"f\u0000\u0000YZ\u0005a\u0000\u0000Z[\u0005l\u0000\u0000[\\\u0005s\u0000"+
		"\u0000\\]\u0005e\u0000\u0000]\u0004\u0001\u0000\u0000\u0000^_\u0005\""+
		"\u0000\u0000_\u0006\u0001\u0000\u0000\u0000`a\u0005\'\u0000\u0000a\b\u0001"+
		"\u0000\u0000\u0000bc\u0005.\u0000\u0000c\n\u0001\u0000\u0000\u0000de\u0005"+
		";\u0000\u0000e\f\u0001\u0000\u0000\u0000fg\u0005:\u0000\u0000g\u000e\u0001"+
		"\u0000\u0000\u0000hi\u0005,\u0000\u0000i\u0010\u0001\u0000\u0000\u0000"+
		"jk\u0005{\u0000\u0000k\u0012\u0001\u0000\u0000\u0000lm\u0005}\u0000\u0000"+
		"m\u0014\u0001\u0000\u0000\u0000no\u0005[\u0000\u0000o\u0016\u0001\u0000"+
		"\u0000\u0000pq\u0005]\u0000\u0000q\u0018\u0001\u0000\u0000\u0000rs\u0005"+
		"<\u0000\u0000s\u001a\u0001\u0000\u0000\u0000tu\u0005>\u0000\u0000u\u001c"+
		"\u0001\u0000\u0000\u0000vw\u0005(\u0000\u0000w\u001e\u0001\u0000\u0000"+
		"\u0000xy\u0005)\u0000\u0000y \u0001\u0000\u0000\u0000z{\u0005-\u0000\u0000"+
		"{\"\u0001\u0000\u0000\u0000|}\u0005+\u0000\u0000}$\u0001\u0000\u0000\u0000"+
		"~\u007f\u0005&\u0000\u0000\u007f&\u0001\u0000\u0000\u0000\u0080\u0081"+
		"\u0005|\u0000\u0000\u0081(\u0001\u0000\u0000\u0000\u0082\u0083\u0005_"+
		"\u0000\u0000\u0083*\u0001\u0000\u0000\u0000\u0084\u0089\u00033\u0019\u0000"+
		"\u0085\u0089\u00031\u0018\u0000\u0086\u0089\u0003/\u0017\u0000\u0087\u0089"+
		"\u0003)\u0014\u0000\u0088\u0084\u0001\u0000\u0000\u0000\u0088\u0085\u0001"+
		"\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0087\u0001"+
		"\u0000\u0000\u0000\u0089,\u0001\u0000\u0000\u0000\u008a\u008c\u0003/\u0017"+
		"\u0000\u008b\u008a\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000"+
		"\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000"+
		"\u0000\u008e.\u0001\u0000\u0000\u0000\u008f\u0090\u0007\u0000\u0000\u0000"+
		"\u00900\u0001\u0000\u0000\u0000\u0091\u0092\u0007\u0001\u0000\u0000\u0092"+
		"2\u0001\u0000\u0000\u0000\u0093\u0094\u0007\u0002\u0000\u0000\u00944\u0001"+
		"\u0000\u0000\u0000\u0095\u0096\u0005t\u0000\u0000\u0096\u0097\u0005h\u0000"+
		"\u0000\u0097\u0098\u0005i\u0000\u0000\u0098\u0099\u0005s\u0000\u0000\u0099"+
		"6\u0001\u0000\u0000\u0000\u009a\u009b\u0005s\u0000\u0000\u009b\u009c\u0005"+
		"u\u0000\u0000\u009c\u009d\u0005p\u0000\u0000\u009d\u009e\u0005e\u0000"+
		"\u0000\u009e\u009f\u0005r\u0000\u0000\u009f8\u0001\u0000\u0000\u0000\u00a0"+
		"\u00a1\u0005o\u0000\u0000\u00a1\u00a2\u0005u\u0000\u0000\u00a2\u00a3\u0005"+
		"t\u0000\u0000\u00a3\u00a4\u0005e\u0000\u0000\u00a4\u00a5\u0005r\u0000"+
		"\u0000\u00a5:\u0001\u0000\u0000\u0000\u00a6\u00a9\u0003\u0001\u0000\u0000"+
		"\u00a7\u00a9\u0003\u0003\u0001\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a9<\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ae\u0003\u0005\u0002\u0000\u00ab\u00ad\u0003+\u0015\u0000\u00ac\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ad\u00b0\u0001\u0000\u0000\u0000\u00ae\u00ac"+
		"\u0001\u0000\u0000\u0000\u00ae\u00af\u0001\u0000\u0000\u0000\u00af\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b1\u00b2"+
		"\u0003\u0005\u0002\u0000\u00b2>\u0001\u0000\u0000\u0000\u00b3\u00b4\u0003"+
		"\u0007\u0003\u0000\u00b4\u00b5\u0003+\u0015\u0000\u00b5\u00b6\u0003\u0007"+
		"\u0003\u0000\u00b6@\u0001\u0000\u0000\u0000\u00b7\u00b9\u0003!\u0010\u0000"+
		"\u00b8\u00b7\u0001\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000\u0000"+
		"\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00bb\u0003-\u0016\u0000\u00bb"+
		"\u00bc\u0003\t\u0004\u0000\u00bc\u00bd\u0003-\u0016\u0000\u00bdB\u0001"+
		"\u0000\u0000\u0000\u00be\u00c0\u0003!\u0010\u0000\u00bf\u00be\u0001\u0000"+
		"\u0000\u0000\u00bf\u00c0\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0003-\u0016\u0000\u00c2D\u0001\u0000\u0000\u0000"+
		"\u00c3\u00ca\u00031\u0018\u0000\u00c4\u00c9\u00033\u0019\u0000\u00c5\u00c9"+
		"\u00031\u0018\u0000\u00c6\u00c9\u0003/\u0017\u0000\u00c7\u00c9\u0003)"+
		"\u0014\u0000\u00c8\u00c4\u0001\u0000\u0000\u0000\u00c8\u00c5\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c8\u00c7\u0001\u0000"+
		"\u0000\u0000\u00c9\u00cc\u0001\u0000\u0000\u0000\u00ca\u00c8\u0001\u0000"+
		"\u0000\u0000\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cbF\u0001\u0000\u0000"+
		"\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cd\u00d4\u00033\u0019\u0000"+
		"\u00ce\u00d3\u00033\u0019\u0000\u00cf\u00d3\u00031\u0018\u0000\u00d0\u00d3"+
		"\u0003/\u0017\u0000\u00d1\u00d3\u0003)\u0014\u0000\u00d2\u00ce\u0001\u0000"+
		"\u0000\u0000\u00d2\u00cf\u0001\u0000\u0000\u0000\u00d2\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d1\u0001\u0000\u0000\u0000\u00d3\u00d6\u0001\u0000"+
		"\u0000\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d4\u00d5\u0001\u0000"+
		"\u0000\u0000\u00d5H\u0001\u0000\u0000\u0000\u00d6\u00d4\u0001\u0000\u0000"+
		"\u0000\u00d7\u00d8\u0003K%\u0000\u00d8\u00d9\u0003\t\u0004\u0000\u00d9"+
		"\u00da\u0003E\"\u0000\u00daJ\u0001\u0000\u0000\u0000\u00db\u00e1\u0003"+
		"G#\u0000\u00dc\u00dd\u0003\t\u0004\u0000\u00dd\u00de\u0003G#\u0000\u00de"+
		"\u00e0\u0001\u0000\u0000\u0000\u00df\u00dc\u0001\u0000\u0000\u0000\u00e0"+
		"\u00e3\u0001\u0000\u0000\u0000\u00e1\u00df\u0001\u0000\u0000\u0000\u00e1"+
		"\u00e2\u0001\u0000\u0000\u0000\u00e2L\u0001\u0000\u0000\u0000\u00e3\u00e1"+
		"\u0001\u0000\u0000\u0000\u00e4\u00e5\u0003\r\u0006\u0000\u00e5N\u0001"+
		"\u0000\u0000\u0000\u00e6\u00e7\u0003\u000b\u0005\u0000\u00e7P\u0001\u0000"+
		"\u0000\u0000\u00e8\u00ea\u0007\u0003\u0000\u0000\u00e9\u00e8\u0001\u0000"+
		"\u0000\u0000\u00ea\u00eb\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001\u0000"+
		"\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001\u0000"+
		"\u0000\u0000\u00ed\u00ee\u0006(\u0000\u0000\u00eeR\u0001\u0000\u0000\u0000"+
		"\r\u0000\u0088\u008d\u00a8\u00ae\u00b8\u00bf\u00c8\u00ca\u00d2\u00d4\u00e1"+
		"\u00eb\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}