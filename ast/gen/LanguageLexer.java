// Generated from F:/git/Alax/ast/src/main/antlr4/language\LanguageLexer.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class LanguageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SHARED=1, PROTECTED=2, VALUE=3, IMPORT=4, ALIAS=5, AS=6, THIS=7, SUPER=8, 
		OUTER=9, RETURN=10, PACKAGE=11, MODULE=12, NOT_FAT_ARROW=13, FAT_ARROW=14, 
		QUOTE=15, APOSTROPHE=16, DOT=17, SEMI_COLON=18, COLON=19, COMMA=20, OPEN_CURLY=21, 
		CLOSE_CURLY=22, OPEN_SQUARE=23, CLOSE_SQUARE=24, OPEN_TRIANGLE=25, CLOSE_TRIANGLE=26, 
		OPEN_BRACKET=27, CLOSE_BRACKET=28, MINUS=29, PLUS=30, AND=31, PIPE=32, 
		UNDERSCORE=33, EQUALS=34, UPPERCASE_WORD=35, LOWERCASE_WORD=36, BOOLEAN_LITERAL=37, 
		STRING_LITERAL=38, CHARACTER_LITERAL=39, FLOAT_LITERAL=40, INTEGER_LITERAL=41, 
		SKIP_WS=42;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SHARED", "PROTECTED", "VALUE", "IMPORT", "ALIAS", "AS", "THIS", "SUPER", 
			"OUTER", "RETURN", "PACKAGE", "MODULE", "TRUE", "FALSE", "NOT_FAT_ARROW", 
			"FAT_ARROW", "QUOTE", "APOSTROPHE", "DOT", "SEMI_COLON", "COLON", "COMMA", 
			"OPEN_CURLY", "CLOSE_CURLY", "OPEN_SQUARE", "CLOSE_SQUARE", "OPEN_TRIANGLE", 
			"CLOSE_TRIANGLE", "OPEN_BRACKET", "CLOSE_BRACKET", "MINUS", "PLUS", "AND", 
			"PIPE", "UNDERSCORE", "EQUALS", "CHARACTER", "DIGITS", "DIGIT", "UPPERCASE_LETTER", 
			"LOWERCASE_LETTER", "SYMBOL", "UPPERCASE_WORD", "LOWERCASE_WORD", "BOOLEAN_LITERAL", 
			"STRING_LITERAL", "CHARACTER_LITERAL", "FLOAT_LITERAL", "INTEGER_LITERAL", 
			"SKIP_WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'shared'", "'protected'", "'value'", "'import'", "'alias'", "'as'", 
			"'this'", "'super'", "'outer'", "'return'", "'package'", "'module'", 
			"'=!>'", "'=>'", "'\"'", "'''", "'.'", "';'", "':'", "','", "'{'", "'}'", 
			"'['", "']'", "'<'", "'>'", "'('", "')'", "'-'", "'+'", "'&'", "'|'", 
			"'_'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SHARED", "PROTECTED", "VALUE", "IMPORT", "ALIAS", "AS", "THIS", 
			"SUPER", "OUTER", "RETURN", "PACKAGE", "MODULE", "NOT_FAT_ARROW", "FAT_ARROW", 
			"QUOTE", "APOSTROPHE", "DOT", "SEMI_COLON", "COLON", "COMMA", "OPEN_CURLY", 
			"CLOSE_CURLY", "OPEN_SQUARE", "CLOSE_SQUARE", "OPEN_TRIANGLE", "CLOSE_TRIANGLE", 
			"OPEN_BRACKET", "CLOSE_BRACKET", "MINUS", "PLUS", "AND", "PIPE", "UNDERSCORE", 
			"EQUALS", "UPPERCASE_WORD", "LOWERCASE_WORD", "BOOLEAN_LITERAL", "STRING_LITERAL", 
			"CHARACTER_LITERAL", "FLOAT_LITERAL", "INTEGER_LITERAL", "SKIP_WS"
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
		"\u0004\u0000*\u0136\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007"+
		"0\u00021\u00071\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001 \u0001"+
		" \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001$\u0001$\u0001"+
		"$\u0003$\u00f2\b$\u0001%\u0004%\u00f5\b%\u000b%\f%\u00f6\u0001&\u0001"+
		"&\u0001\'\u0001\'\u0001(\u0001(\u0001)\u0001)\u0001)\u0001)\u0003)\u0103"+
		"\b)\u0001*\u0001*\u0005*\u0107\b*\n*\f*\u010a\t*\u0001+\u0001+\u0005+"+
		"\u010e\b+\n+\f+\u0111\t+\u0001,\u0001,\u0003,\u0115\b,\u0001-\u0001-\u0005"+
		"-\u0119\b-\n-\f-\u011c\t-\u0001-\u0001-\u0001.\u0001.\u0001.\u0001.\u0001"+
		"/\u0003/\u0125\b/\u0001/\u0001/\u0001/\u0001/\u00010\u00030\u012c\b0\u0001"+
		"0\u00010\u00011\u00041\u0131\b1\u000b1\f1\u0132\u00011\u00011\u0000\u0000"+
		"2\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\u0000\u001b"+
		"\u0000\u001d\r\u001f\u000e!\u000f#\u0010%\u0011\'\u0012)\u0013+\u0014"+
		"-\u0015/\u00161\u00173\u00185\u00197\u001a9\u001b;\u001c=\u001d?\u001e"+
		"A\u001fC E!G\"I\u0000K\u0000M\u0000O\u0000Q\u0000S\u0000U#W$Y%[&]\'_("+
		"a)c*\u0001\u0000\u0004\u0001\u000009\u0001\u0000AZ\u0001\u0000az\u0003"+
		"\u0000\t\n\r\r  \u013b\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003"+
		"\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007"+
		"\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
		"\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
		"\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000"+
		")\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001"+
		"\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000"+
		"\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u0000"+
		"7\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001"+
		"\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000"+
		"\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000"+
		"E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000U\u0001"+
		"\u0000\u0000\u0000\u0000W\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000"+
		"\u0000\u0000[\u0001\u0000\u0000\u0000\u0000]\u0001\u0000\u0000\u0000\u0000"+
		"_\u0001\u0000\u0000\u0000\u0000a\u0001\u0000\u0000\u0000\u0000c\u0001"+
		"\u0000\u0000\u0000\u0001e\u0001\u0000\u0000\u0000\u0003l\u0001\u0000\u0000"+
		"\u0000\u0005v\u0001\u0000\u0000\u0000\u0007|\u0001\u0000\u0000\u0000\t"+
		"\u0083\u0001\u0000\u0000\u0000\u000b\u0089\u0001\u0000\u0000\u0000\r\u008c"+
		"\u0001\u0000\u0000\u0000\u000f\u0091\u0001\u0000\u0000\u0000\u0011\u0097"+
		"\u0001\u0000\u0000\u0000\u0013\u009d\u0001\u0000\u0000\u0000\u0015\u00a4"+
		"\u0001\u0000\u0000\u0000\u0017\u00ac\u0001\u0000\u0000\u0000\u0019\u00b3"+
		"\u0001\u0000\u0000\u0000\u001b\u00b8\u0001\u0000\u0000\u0000\u001d\u00be"+
		"\u0001\u0000\u0000\u0000\u001f\u00c2\u0001\u0000\u0000\u0000!\u00c5\u0001"+
		"\u0000\u0000\u0000#\u00c7\u0001\u0000\u0000\u0000%\u00c9\u0001\u0000\u0000"+
		"\u0000\'\u00cb\u0001\u0000\u0000\u0000)\u00cd\u0001\u0000\u0000\u0000"+
		"+\u00cf\u0001\u0000\u0000\u0000-\u00d1\u0001\u0000\u0000\u0000/\u00d3"+
		"\u0001\u0000\u0000\u00001\u00d5\u0001\u0000\u0000\u00003\u00d7\u0001\u0000"+
		"\u0000\u00005\u00d9\u0001\u0000\u0000\u00007\u00db\u0001\u0000\u0000\u0000"+
		"9\u00dd\u0001\u0000\u0000\u0000;\u00df\u0001\u0000\u0000\u0000=\u00e1"+
		"\u0001\u0000\u0000\u0000?\u00e3\u0001\u0000\u0000\u0000A\u00e5\u0001\u0000"+
		"\u0000\u0000C\u00e7\u0001\u0000\u0000\u0000E\u00e9\u0001\u0000\u0000\u0000"+
		"G\u00eb\u0001\u0000\u0000\u0000I\u00f1\u0001\u0000\u0000\u0000K\u00f4"+
		"\u0001\u0000\u0000\u0000M\u00f8\u0001\u0000\u0000\u0000O\u00fa\u0001\u0000"+
		"\u0000\u0000Q\u00fc\u0001\u0000\u0000\u0000S\u0102\u0001\u0000\u0000\u0000"+
		"U\u0104\u0001\u0000\u0000\u0000W\u010b\u0001\u0000\u0000\u0000Y\u0114"+
		"\u0001\u0000\u0000\u0000[\u0116\u0001\u0000\u0000\u0000]\u011f\u0001\u0000"+
		"\u0000\u0000_\u0124\u0001\u0000\u0000\u0000a\u012b\u0001\u0000\u0000\u0000"+
		"c\u0130\u0001\u0000\u0000\u0000ef\u0005s\u0000\u0000fg\u0005h\u0000\u0000"+
		"gh\u0005a\u0000\u0000hi\u0005r\u0000\u0000ij\u0005e\u0000\u0000jk\u0005"+
		"d\u0000\u0000k\u0002\u0001\u0000\u0000\u0000lm\u0005p\u0000\u0000mn\u0005"+
		"r\u0000\u0000no\u0005o\u0000\u0000op\u0005t\u0000\u0000pq\u0005e\u0000"+
		"\u0000qr\u0005c\u0000\u0000rs\u0005t\u0000\u0000st\u0005e\u0000\u0000"+
		"tu\u0005d\u0000\u0000u\u0004\u0001\u0000\u0000\u0000vw\u0005v\u0000\u0000"+
		"wx\u0005a\u0000\u0000xy\u0005l\u0000\u0000yz\u0005u\u0000\u0000z{\u0005"+
		"e\u0000\u0000{\u0006\u0001\u0000\u0000\u0000|}\u0005i\u0000\u0000}~\u0005"+
		"m\u0000\u0000~\u007f\u0005p\u0000\u0000\u007f\u0080\u0005o\u0000\u0000"+
		"\u0080\u0081\u0005r\u0000\u0000\u0081\u0082\u0005t\u0000\u0000\u0082\b"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\u0005a\u0000\u0000\u0084\u0085\u0005"+
		"l\u0000\u0000\u0085\u0086\u0005i\u0000\u0000\u0086\u0087\u0005a\u0000"+
		"\u0000\u0087\u0088\u0005s\u0000\u0000\u0088\n\u0001\u0000\u0000\u0000"+
		"\u0089\u008a\u0005a\u0000\u0000\u008a\u008b\u0005s\u0000\u0000\u008b\f"+
		"\u0001\u0000\u0000\u0000\u008c\u008d\u0005t\u0000\u0000\u008d\u008e\u0005"+
		"h\u0000\u0000\u008e\u008f\u0005i\u0000\u0000\u008f\u0090\u0005s\u0000"+
		"\u0000\u0090\u000e\u0001\u0000\u0000\u0000\u0091\u0092\u0005s\u0000\u0000"+
		"\u0092\u0093\u0005u\u0000\u0000\u0093\u0094\u0005p\u0000\u0000\u0094\u0095"+
		"\u0005e\u0000\u0000\u0095\u0096\u0005r\u0000\u0000\u0096\u0010\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u0005o\u0000\u0000\u0098\u0099\u0005u\u0000\u0000"+
		"\u0099\u009a\u0005t\u0000\u0000\u009a\u009b\u0005e\u0000\u0000\u009b\u009c"+
		"\u0005r\u0000\u0000\u009c\u0012\u0001\u0000\u0000\u0000\u009d\u009e\u0005"+
		"r\u0000\u0000\u009e\u009f\u0005e\u0000\u0000\u009f\u00a0\u0005t\u0000"+
		"\u0000\u00a0\u00a1\u0005u\u0000\u0000\u00a1\u00a2\u0005r\u0000\u0000\u00a2"+
		"\u00a3\u0005n\u0000\u0000\u00a3\u0014\u0001\u0000\u0000\u0000\u00a4\u00a5"+
		"\u0005p\u0000\u0000\u00a5\u00a6\u0005a\u0000\u0000\u00a6\u00a7\u0005c"+
		"\u0000\u0000\u00a7\u00a8\u0005k\u0000\u0000\u00a8\u00a9\u0005a\u0000\u0000"+
		"\u00a9\u00aa\u0005g\u0000\u0000\u00aa\u00ab\u0005e\u0000\u0000\u00ab\u0016"+
		"\u0001\u0000\u0000\u0000\u00ac\u00ad\u0005m\u0000\u0000\u00ad\u00ae\u0005"+
		"o\u0000\u0000\u00ae\u00af\u0005d\u0000\u0000\u00af\u00b0\u0005u\u0000"+
		"\u0000\u00b0\u00b1\u0005l\u0000\u0000\u00b1\u00b2\u0005e\u0000\u0000\u00b2"+
		"\u0018\u0001\u0000\u0000\u0000\u00b3\u00b4\u0005t\u0000\u0000\u00b4\u00b5"+
		"\u0005r\u0000\u0000\u00b5\u00b6\u0005u\u0000\u0000\u00b6\u00b7\u0005e"+
		"\u0000\u0000\u00b7\u001a\u0001\u0000\u0000\u0000\u00b8\u00b9\u0005f\u0000"+
		"\u0000\u00b9\u00ba\u0005a\u0000\u0000\u00ba\u00bb\u0005l\u0000\u0000\u00bb"+
		"\u00bc\u0005s\u0000\u0000\u00bc\u00bd\u0005e\u0000\u0000\u00bd\u001c\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0005=\u0000\u0000\u00bf\u00c0\u0005!\u0000"+
		"\u0000\u00c0\u00c1\u0005>\u0000\u0000\u00c1\u001e\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c3\u0005=\u0000\u0000\u00c3\u00c4\u0005>\u0000\u0000\u00c4 "+
		"\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\"\u0000\u0000\u00c6\"\u0001"+
		"\u0000\u0000\u0000\u00c7\u00c8\u0005\'\u0000\u0000\u00c8$\u0001\u0000"+
		"\u0000\u0000\u00c9\u00ca\u0005.\u0000\u0000\u00ca&\u0001\u0000\u0000\u0000"+
		"\u00cb\u00cc\u0005;\u0000\u0000\u00cc(\u0001\u0000\u0000\u0000\u00cd\u00ce"+
		"\u0005:\u0000\u0000\u00ce*\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005,"+
		"\u0000\u0000\u00d0,\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005{\u0000\u0000"+
		"\u00d2.\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005}\u0000\u0000\u00d40"+
		"\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005[\u0000\u0000\u00d62\u0001\u0000"+
		"\u0000\u0000\u00d7\u00d8\u0005]\u0000\u0000\u00d84\u0001\u0000\u0000\u0000"+
		"\u00d9\u00da\u0005<\u0000\u0000\u00da6\u0001\u0000\u0000\u0000\u00db\u00dc"+
		"\u0005>\u0000\u0000\u00dc8\u0001\u0000\u0000\u0000\u00dd\u00de\u0005("+
		"\u0000\u0000\u00de:\u0001\u0000\u0000\u0000\u00df\u00e0\u0005)\u0000\u0000"+
		"\u00e0<\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005-\u0000\u0000\u00e2>"+
		"\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005+\u0000\u0000\u00e4@\u0001\u0000"+
		"\u0000\u0000\u00e5\u00e6\u0005&\u0000\u0000\u00e6B\u0001\u0000\u0000\u0000"+
		"\u00e7\u00e8\u0005|\u0000\u0000\u00e8D\u0001\u0000\u0000\u0000\u00e9\u00ea"+
		"\u0005_\u0000\u0000\u00eaF\u0001\u0000\u0000\u0000\u00eb\u00ec\u0005="+
		"\u0000\u0000\u00ecH\u0001\u0000\u0000\u0000\u00ed\u00f2\u0003Q(\u0000"+
		"\u00ee\u00f2\u0003O\'\u0000\u00ef\u00f2\u0003M&\u0000\u00f0\u00f2\u0003"+
		"E\"\u0000\u00f1\u00ed\u0001\u0000\u0000\u0000\u00f1\u00ee\u0001\u0000"+
		"\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f1\u00f0\u0001\u0000"+
		"\u0000\u0000\u00f2J\u0001\u0000\u0000\u0000\u00f3\u00f5\u0003M&\u0000"+
		"\u00f4\u00f3\u0001\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000\u0000\u0000"+
		"\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000"+
		"\u00f7L\u0001\u0000\u0000\u0000\u00f8\u00f9\u0007\u0000\u0000\u0000\u00f9"+
		"N\u0001\u0000\u0000\u0000\u00fa\u00fb\u0007\u0001\u0000\u0000\u00fbP\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0007\u0002\u0000\u0000\u00fdR\u0001\u0000"+
		"\u0000\u0000\u00fe\u0103\u0003Q(\u0000\u00ff\u0103\u0003O\'\u0000\u0100"+
		"\u0103\u0003M&\u0000\u0101\u0103\u0003E\"\u0000\u0102\u00fe\u0001\u0000"+
		"\u0000\u0000\u0102\u00ff\u0001\u0000\u0000\u0000\u0102\u0100\u0001\u0000"+
		"\u0000\u0000\u0102\u0101\u0001\u0000\u0000\u0000\u0103T\u0001\u0000\u0000"+
		"\u0000\u0104\u0108\u0003O\'\u0000\u0105\u0107\u0003S)\u0000\u0106\u0105"+
		"\u0001\u0000\u0000\u0000\u0107\u010a\u0001\u0000\u0000\u0000\u0108\u0106"+
		"\u0001\u0000\u0000\u0000\u0108\u0109\u0001\u0000\u0000\u0000\u0109V\u0001"+
		"\u0000\u0000\u0000\u010a\u0108\u0001\u0000\u0000\u0000\u010b\u010f\u0003"+
		"Q(\u0000\u010c\u010e\u0003S)\u0000\u010d\u010c\u0001\u0000\u0000\u0000"+
		"\u010e\u0111\u0001\u0000\u0000\u0000\u010f\u010d\u0001\u0000\u0000\u0000"+
		"\u010f\u0110\u0001\u0000\u0000\u0000\u0110X\u0001\u0000\u0000\u0000\u0111"+
		"\u010f\u0001\u0000\u0000\u0000\u0112\u0115\u0003\u0019\f\u0000\u0113\u0115"+
		"\u0003\u001b\r\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0114\u0113\u0001"+
		"\u0000\u0000\u0000\u0115Z\u0001\u0000\u0000\u0000\u0116\u011a\u0003!\u0010"+
		"\u0000\u0117\u0119\u0003I$\u0000\u0118\u0117\u0001\u0000\u0000\u0000\u0119"+
		"\u011c\u0001\u0000\u0000\u0000\u011a\u0118\u0001\u0000\u0000\u0000\u011a"+
		"\u011b\u0001\u0000\u0000\u0000\u011b\u011d\u0001\u0000\u0000\u0000\u011c"+
		"\u011a\u0001\u0000\u0000\u0000\u011d\u011e\u0003!\u0010\u0000\u011e\\"+
		"\u0001\u0000\u0000\u0000\u011f\u0120\u0003#\u0011\u0000\u0120\u0121\u0003"+
		"I$\u0000\u0121\u0122\u0003#\u0011\u0000\u0122^\u0001\u0000\u0000\u0000"+
		"\u0123\u0125\u0003=\u001e\u0000\u0124\u0123\u0001\u0000\u0000\u0000\u0124"+
		"\u0125\u0001\u0000\u0000\u0000\u0125\u0126\u0001\u0000\u0000\u0000\u0126"+
		"\u0127\u0003K%\u0000\u0127\u0128\u0003%\u0012\u0000\u0128\u0129\u0003"+
		"K%\u0000\u0129`\u0001\u0000\u0000\u0000\u012a\u012c\u0003=\u001e\u0000"+
		"\u012b\u012a\u0001\u0000\u0000\u0000\u012b\u012c\u0001\u0000\u0000\u0000"+
		"\u012c\u012d\u0001\u0000\u0000\u0000\u012d\u012e\u0003K%\u0000\u012eb"+
		"\u0001\u0000\u0000\u0000\u012f\u0131\u0007\u0003\u0000\u0000\u0130\u012f"+
		"\u0001\u0000\u0000\u0000\u0131\u0132\u0001\u0000\u0000\u0000\u0132\u0130"+
		"\u0001\u0000\u0000\u0000\u0132\u0133\u0001\u0000\u0000\u0000\u0133\u0134"+
		"\u0001\u0000\u0000\u0000\u0134\u0135\u00061\u0000\u0000\u0135d\u0001\u0000"+
		"\u0000\u0000\u000b\u0000\u00f1\u00f6\u0102\u0108\u010f\u0114\u011a\u0124"+
		"\u012b\u0132\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}