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
		UNDERSCORE=33, EQUALS=34, BOOLEAN_LITERAL=35, STRING_LITERAL=36, CHARACTER_LITERAL=37, 
		FLOAT_LITERAL=38, INTEGER_LITERAL=39, SKIP_WS=40, UPPERCASE_IDENTIFIER=41, 
		LOWERCASE_IDENTIFIER=42, WS=43, UPPERCASE_NAME=44, QUALIFIED_UPPERCASE_NAME=45, 
		LOWERCASE_NAME=46, QUALIFIED_LOWERCASE_NAME=47;
	public static final int
		Spacefull=1, Spaceless=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "Spacefull", "Spaceless"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SHARED", "PROTECTED", "VALUE", "IMPORT", "ALIAS", "AS", "THIS", "SUPER", 
			"OUTER", "RETURN", "PACKAGE", "MODULE", "TRUE", "FALSE", "NOT_FAT_ARROW", 
			"FAT_ARROW", "QUOTE", "APOSTROPHE", "DOT", "SEMI_COLON", "COLON", "COMMA", 
			"OPEN_CURLY", "CLOSE_CURLY", "OPEN_SQUARE", "CLOSE_SQUARE", "OPEN_TRIANGLE", 
			"CLOSE_TRIANGLE", "OPEN_BRACKET", "CLOSE_BRACKET", "MINUS", "PLUS", "AND", 
			"PIPE", "UNDERSCORE", "EQUALS", "CHARACTER", "DIGITS", "DIGIT", "UPPERCASE_LETTER", 
			"LOWERCASE_LETTER", "SYMBOL", "UPPERCASE_IDENTIFIER_PART", "LOWERCASE_IDENTIFIER_PART", 
			"UPPERCASE_NAME_PART", "LOWERCASE_NAME_PART", "BOOLEAN_LITERAL", "STRING_LITERAL", 
			"CHARACTER_LITERAL", "FLOAT_LITERAL", "INTEGER_LITERAL", "SKIP_WS", "UPPERCASE_IDENTIFIER", 
			"LOWERCASE_IDENTIFIER", "WS", "UPPERCASE_NAME", "QUALIFIED_UPPERCASE_NAME", 
			"LOWERCASE_NAME", "QUALIFIED_LOWERCASE_NAME"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'shared'", "'protected'", null, "'import'", "'alias'", "'as'", 
			"'this'", "'super'", "'outer'", "'return'", null, null, "'=!>'", "'=>'", 
			"'\"'", "'''", "'.'", "';'", "':'", "','", "'{'", "'}'", "'['", "']'", 
			"'<'", "'>'", "'('", "')'", "'-'", "'+'", "'&'", "'|'", "'_'", "'='"
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
			"EQUALS", "BOOLEAN_LITERAL", "STRING_LITERAL", "CHARACTER_LITERAL", "FLOAT_LITERAL", 
			"INTEGER_LITERAL", "SKIP_WS", "UPPERCASE_IDENTIFIER", "LOWERCASE_IDENTIFIER", 
			"WS", "UPPERCASE_NAME", "QUALIFIED_UPPERCASE_NAME", "LOWERCASE_NAME", 
			"QUALIFIED_LOWERCASE_NAME"
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
		"\u0004\u0000/\u019b\u0006\uffff\uffff\u0006\uffff\uffff\u0006\uffff\uffff"+
		"\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002"+
		"\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005"+
		"\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002"+
		"\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002\f\u0007\f\u0002"+
		"\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f\u0002\u0010"+
		"\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012\u0002\u0013"+
		"\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015\u0002\u0016"+
		"\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018\u0002\u0019"+
		"\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b\u0002\u001c"+
		"\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e\u0002\u001f"+
		"\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002#\u0007"+
		"#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002(\u0007"+
		"(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002-\u0007"+
		"-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u00071\u00022\u0007"+
		"2\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u00076\u00027\u0007"+
		"7\u00028\u00078\u00029\u00079\u0002:\u0007:\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001 \u0001"+
		" \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001$\u0001$\u0001"+
		"$\u0003$\u0112\b$\u0001%\u0004%\u0115\b%\u000b%\f%\u0116\u0001&\u0001"+
		"&\u0001\'\u0001\'\u0001(\u0001(\u0001)\u0001)\u0001)\u0001)\u0003)\u0123"+
		"\b)\u0001*\u0001*\u0005*\u0127\b*\n*\f*\u012a\t*\u0001+\u0001+\u0005+"+
		"\u012e\b+\n+\f+\u0131\t+\u0001,\u0001,\u0005,\u0135\b,\n,\f,\u0138\t,"+
		"\u0001-\u0001-\u0005-\u013c\b-\n-\f-\u013f\t-\u0001.\u0001.\u0003.\u0143"+
		"\b.\u0001/\u0001/\u0005/\u0147\b/\n/\f/\u014a\t/\u0001/\u0001/\u00010"+
		"\u00010\u00010\u00010\u00011\u00031\u0153\b1\u00011\u00011\u00011\u0001"+
		"1\u00012\u00032\u015a\b2\u00012\u00012\u00013\u00043\u015f\b3\u000b3\f"+
		"3\u0160\u00013\u00013\u00014\u00014\u00014\u00014\u00054\u0169\b4\n4\f"+
		"4\u016c\t4\u00015\u00015\u00015\u00015\u00055\u0172\b5\n5\f5\u0175\t5"+
		"\u00015\u00015\u00016\u00046\u017a\b6\u000b6\f6\u017b\u00017\u00017\u0001"+
		"7\u00017\u00018\u00018\u00018\u00018\u00058\u0186\b8\n8\f8\u0189\t8\u0001"+
		"8\u00018\u00019\u00019\u00019\u00019\u0001:\u0001:\u0001:\u0001:\u0005"+
		":\u0195\b:\n:\f:\u0198\t:\u0001:\u0001:\u0000\u0000;\u0003\u0001\u0005"+
		"\u0002\u0007\u0003\t\u0004\u000b\u0005\r\u0006\u000f\u0007\u0011\b\u0013"+
		"\t\u0015\n\u0017\u000b\u0019\f\u001b\u0000\u001d\u0000\u001f\r!\u000e"+
		"#\u000f%\u0010\'\u0011)\u0012+\u0013-\u0014/\u00151\u00163\u00175\u0018"+
		"7\u00199\u001a;\u001b=\u001c?\u001dA\u001eC\u001fE G!I\"K\u0000M\u0000"+
		"O\u0000Q\u0000S\u0000U\u0000W\u0000Y\u0000[\u0000]\u0000_#a$c%e&g\'i("+
		"k)m*o+q,s-u.w/\u0003\u0000\u0001\u0002\u0004\u0001\u000009\u0001\u0000"+
		"AZ\u0001\u0000az\u0003\u0000\t\n\r\r  \u01a1\u0000\u0003\u0001\u0000\u0000"+
		"\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000"+
		"\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000"+
		"\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000"+
		"\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000"+
		"\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000"+
		"\u0019\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000"+
		"!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001"+
		"\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000"+
		"\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000"+
		"\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003"+
		"\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000"+
		"\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000\u0000"+
		"\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000A"+
		"\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001\u0000"+
		"\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000\u0000"+
		"\u0000_\u0001\u0000\u0000\u0000\u0000a\u0001\u0000\u0000\u0000\u0000c"+
		"\u0001\u0000\u0000\u0000\u0000e\u0001\u0000\u0000\u0000\u0000g\u0001\u0000"+
		"\u0000\u0000\u0000i\u0001\u0000\u0000\u0000\u0001k\u0001\u0000\u0000\u0000"+
		"\u0001m\u0001\u0000\u0000\u0000\u0001o\u0001\u0000\u0000\u0000\u0002q"+
		"\u0001\u0000\u0000\u0000\u0002s\u0001\u0000\u0000\u0000\u0002u\u0001\u0000"+
		"\u0000\u0000\u0002w\u0001\u0000\u0000\u0000\u0003y\u0001\u0000\u0000\u0000"+
		"\u0005\u0080\u0001\u0000\u0000\u0000\u0007\u008a\u0001\u0000\u0000\u0000"+
		"\t\u0094\u0001\u0000\u0000\u0000\u000b\u009b\u0001\u0000\u0000\u0000\r"+
		"\u00a1\u0001\u0000\u0000\u0000\u000f\u00a4\u0001\u0000\u0000\u0000\u0011"+
		"\u00a9\u0001\u0000\u0000\u0000\u0013\u00af\u0001\u0000\u0000\u0000\u0015"+
		"\u00b5\u0001\u0000\u0000\u0000\u0017\u00bc\u0001\u0000\u0000\u0000\u0019"+
		"\u00c8\u0001\u0000\u0000\u0000\u001b\u00d3\u0001\u0000\u0000\u0000\u001d"+
		"\u00d8\u0001\u0000\u0000\u0000\u001f\u00de\u0001\u0000\u0000\u0000!\u00e2"+
		"\u0001\u0000\u0000\u0000#\u00e5\u0001\u0000\u0000\u0000%\u00e7\u0001\u0000"+
		"\u0000\u0000\'\u00e9\u0001\u0000\u0000\u0000)\u00eb\u0001\u0000\u0000"+
		"\u0000+\u00ed\u0001\u0000\u0000\u0000-\u00ef\u0001\u0000\u0000\u0000/"+
		"\u00f1\u0001\u0000\u0000\u00001\u00f3\u0001\u0000\u0000\u00003\u00f5\u0001"+
		"\u0000\u0000\u00005\u00f7\u0001\u0000\u0000\u00007\u00f9\u0001\u0000\u0000"+
		"\u00009\u00fb\u0001\u0000\u0000\u0000;\u00fd\u0001\u0000\u0000\u0000="+
		"\u00ff\u0001\u0000\u0000\u0000?\u0101\u0001\u0000\u0000\u0000A\u0103\u0001"+
		"\u0000\u0000\u0000C\u0105\u0001\u0000\u0000\u0000E\u0107\u0001\u0000\u0000"+
		"\u0000G\u0109\u0001\u0000\u0000\u0000I\u010b\u0001\u0000\u0000\u0000K"+
		"\u0111\u0001\u0000\u0000\u0000M\u0114\u0001\u0000\u0000\u0000O\u0118\u0001"+
		"\u0000\u0000\u0000Q\u011a\u0001\u0000\u0000\u0000S\u011c\u0001\u0000\u0000"+
		"\u0000U\u0122\u0001\u0000\u0000\u0000W\u0124\u0001\u0000\u0000\u0000Y"+
		"\u012b\u0001\u0000\u0000\u0000[\u0132\u0001\u0000\u0000\u0000]\u0139\u0001"+
		"\u0000\u0000\u0000_\u0142\u0001\u0000\u0000\u0000a\u0144\u0001\u0000\u0000"+
		"\u0000c\u014d\u0001\u0000\u0000\u0000e\u0152\u0001\u0000\u0000\u0000g"+
		"\u0159\u0001\u0000\u0000\u0000i\u015e\u0001\u0000\u0000\u0000k\u0164\u0001"+
		"\u0000\u0000\u0000m\u016d\u0001\u0000\u0000\u0000o\u0179\u0001\u0000\u0000"+
		"\u0000q\u017d\u0001\u0000\u0000\u0000s\u0181\u0001\u0000\u0000\u0000u"+
		"\u018c\u0001\u0000\u0000\u0000w\u0190\u0001\u0000\u0000\u0000yz\u0005"+
		"s\u0000\u0000z{\u0005h\u0000\u0000{|\u0005a\u0000\u0000|}\u0005r\u0000"+
		"\u0000}~\u0005e\u0000\u0000~\u007f\u0005d\u0000\u0000\u007f\u0004\u0001"+
		"\u0000\u0000\u0000\u0080\u0081\u0005p\u0000\u0000\u0081\u0082\u0005r\u0000"+
		"\u0000\u0082\u0083\u0005o\u0000\u0000\u0083\u0084\u0005t\u0000\u0000\u0084"+
		"\u0085\u0005e\u0000\u0000\u0085\u0086\u0005c\u0000\u0000\u0086\u0087\u0005"+
		"t\u0000\u0000\u0087\u0088\u0005e\u0000\u0000\u0088\u0089\u0005d\u0000"+
		"\u0000\u0089\u0006\u0001\u0000\u0000\u0000\u008a\u008b\u0005v\u0000\u0000"+
		"\u008b\u008c\u0005a\u0000\u0000\u008c\u008d\u0005l\u0000\u0000\u008d\u008e"+
		"\u0005u\u0000\u0000\u008e\u008f\u0005e\u0000\u0000\u008f\u0090\u0001\u0000"+
		"\u0000\u0000\u0090\u0091\u0003i3\u0000\u0091\u0092\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0006\u0002\u0000\u0000\u0093\b\u0001\u0000\u0000\u0000\u0094"+
		"\u0095\u0005i\u0000\u0000\u0095\u0096\u0005m\u0000\u0000\u0096\u0097\u0005"+
		"p\u0000\u0000\u0097\u0098\u0005o\u0000\u0000\u0098\u0099\u0005r\u0000"+
		"\u0000\u0099\u009a\u0005t\u0000\u0000\u009a\n\u0001\u0000\u0000\u0000"+
		"\u009b\u009c\u0005a\u0000\u0000\u009c\u009d\u0005l\u0000\u0000\u009d\u009e"+
		"\u0005i\u0000\u0000\u009e\u009f\u0005a\u0000\u0000\u009f\u00a0\u0005s"+
		"\u0000\u0000\u00a0\f\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005a\u0000"+
		"\u0000\u00a2\u00a3\u0005s\u0000\u0000\u00a3\u000e\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a5\u0005t\u0000\u0000\u00a5\u00a6\u0005h\u0000\u0000\u00a6\u00a7"+
		"\u0005i\u0000\u0000\u00a7\u00a8\u0005s\u0000\u0000\u00a8\u0010\u0001\u0000"+
		"\u0000\u0000\u00a9\u00aa\u0005s\u0000\u0000\u00aa\u00ab\u0005u\u0000\u0000"+
		"\u00ab\u00ac\u0005p\u0000\u0000\u00ac\u00ad\u0005e\u0000\u0000\u00ad\u00ae"+
		"\u0005r\u0000\u0000\u00ae\u0012\u0001\u0000\u0000\u0000\u00af\u00b0\u0005"+
		"o\u0000\u0000\u00b0\u00b1\u0005u\u0000\u0000\u00b1\u00b2\u0005t\u0000"+
		"\u0000\u00b2\u00b3\u0005e\u0000\u0000\u00b3\u00b4\u0005r\u0000\u0000\u00b4"+
		"\u0014\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005r\u0000\u0000\u00b6\u00b7"+
		"\u0005e\u0000\u0000\u00b7\u00b8\u0005t\u0000\u0000\u00b8\u00b9\u0005u"+
		"\u0000\u0000\u00b9\u00ba\u0005r\u0000\u0000\u00ba\u00bb\u0005n\u0000\u0000"+
		"\u00bb\u0016\u0001\u0000\u0000\u0000\u00bc\u00bd\u0005p\u0000\u0000\u00bd"+
		"\u00be\u0005a\u0000\u0000\u00be\u00bf\u0005c\u0000\u0000\u00bf\u00c0\u0005"+
		"k\u0000\u0000\u00c0\u00c1\u0005a\u0000\u0000\u00c1\u00c2\u0005g\u0000"+
		"\u0000\u00c2\u00c3\u0005e\u0000\u0000\u00c3\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c5\u0003i3\u0000\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c7"+
		"\u0006\n\u0001\u0000\u00c7\u0018\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005"+
		"m\u0000\u0000\u00c9\u00ca\u0005o\u0000\u0000\u00ca\u00cb\u0005d\u0000"+
		"\u0000\u00cb\u00cc\u0005u\u0000\u0000\u00cc\u00cd\u0005l\u0000\u0000\u00cd"+
		"\u00ce\u0005e\u0000\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000\u00cf\u00d0"+
		"\u0003i3\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u00d2\u0006\u000b"+
		"\u0001\u0000\u00d2\u001a\u0001\u0000\u0000\u0000\u00d3\u00d4\u0005t\u0000"+
		"\u0000\u00d4\u00d5\u0005r\u0000\u0000\u00d5\u00d6\u0005u\u0000\u0000\u00d6"+
		"\u00d7\u0005e\u0000\u0000\u00d7\u001c\u0001\u0000\u0000\u0000\u00d8\u00d9"+
		"\u0005f\u0000\u0000\u00d9\u00da\u0005a\u0000\u0000\u00da\u00db\u0005l"+
		"\u0000\u0000\u00db\u00dc\u0005s\u0000\u0000\u00dc\u00dd\u0005e\u0000\u0000"+
		"\u00dd\u001e\u0001\u0000\u0000\u0000\u00de\u00df\u0005=\u0000\u0000\u00df"+
		"\u00e0\u0005!\u0000\u0000\u00e0\u00e1\u0005>\u0000\u0000\u00e1 \u0001"+
		"\u0000\u0000\u0000\u00e2\u00e3\u0005=\u0000\u0000\u00e3\u00e4\u0005>\u0000"+
		"\u0000\u00e4\"\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005\"\u0000\u0000"+
		"\u00e6$\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005\'\u0000\u0000\u00e8"+
		"&\u0001\u0000\u0000\u0000\u00e9\u00ea\u0005.\u0000\u0000\u00ea(\u0001"+
		"\u0000\u0000\u0000\u00eb\u00ec\u0005;\u0000\u0000\u00ec*\u0001\u0000\u0000"+
		"\u0000\u00ed\u00ee\u0005:\u0000\u0000\u00ee,\u0001\u0000\u0000\u0000\u00ef"+
		"\u00f0\u0005,\u0000\u0000\u00f0.\u0001\u0000\u0000\u0000\u00f1\u00f2\u0005"+
		"{\u0000\u0000\u00f20\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005}\u0000"+
		"\u0000\u00f42\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005[\u0000\u0000\u00f6"+
		"4\u0001\u0000\u0000\u0000\u00f7\u00f8\u0005]\u0000\u0000\u00f86\u0001"+
		"\u0000\u0000\u0000\u00f9\u00fa\u0005<\u0000\u0000\u00fa8\u0001\u0000\u0000"+
		"\u0000\u00fb\u00fc\u0005>\u0000\u0000\u00fc:\u0001\u0000\u0000\u0000\u00fd"+
		"\u00fe\u0005(\u0000\u0000\u00fe<\u0001\u0000\u0000\u0000\u00ff\u0100\u0005"+
		")\u0000\u0000\u0100>\u0001\u0000\u0000\u0000\u0101\u0102\u0005-\u0000"+
		"\u0000\u0102@\u0001\u0000\u0000\u0000\u0103\u0104\u0005+\u0000\u0000\u0104"+
		"B\u0001\u0000\u0000\u0000\u0105\u0106\u0005&\u0000\u0000\u0106D\u0001"+
		"\u0000\u0000\u0000\u0107\u0108\u0005|\u0000\u0000\u0108F\u0001\u0000\u0000"+
		"\u0000\u0109\u010a\u0005_\u0000\u0000\u010aH\u0001\u0000\u0000\u0000\u010b"+
		"\u010c\u0005=\u0000\u0000\u010cJ\u0001\u0000\u0000\u0000\u010d\u0112\u0003"+
		"S(\u0000\u010e\u0112\u0003Q\'\u0000\u010f\u0112\u0003O&\u0000\u0110\u0112"+
		"\u0003G\"\u0000\u0111\u010d\u0001\u0000\u0000\u0000\u0111\u010e\u0001"+
		"\u0000\u0000\u0000\u0111\u010f\u0001\u0000\u0000\u0000\u0111\u0110\u0001"+
		"\u0000\u0000\u0000\u0112L\u0001\u0000\u0000\u0000\u0113\u0115\u0003O&"+
		"\u0000\u0114\u0113\u0001\u0000\u0000\u0000\u0115\u0116\u0001\u0000\u0000"+
		"\u0000\u0116\u0114\u0001\u0000\u0000\u0000\u0116\u0117\u0001\u0000\u0000"+
		"\u0000\u0117N\u0001\u0000\u0000\u0000\u0118\u0119\u0007\u0000\u0000\u0000"+
		"\u0119P\u0001\u0000\u0000\u0000\u011a\u011b\u0007\u0001\u0000\u0000\u011b"+
		"R\u0001\u0000\u0000\u0000\u011c\u011d\u0007\u0002\u0000\u0000\u011dT\u0001"+
		"\u0000\u0000\u0000\u011e\u0123\u0003S(\u0000\u011f\u0123\u0003Q\'\u0000"+
		"\u0120\u0123\u0003O&\u0000\u0121\u0123\u0003G\"\u0000\u0122\u011e\u0001"+
		"\u0000\u0000\u0000\u0122\u011f\u0001\u0000\u0000\u0000\u0122\u0120\u0001"+
		"\u0000\u0000\u0000\u0122\u0121\u0001\u0000\u0000\u0000\u0123V\u0001\u0000"+
		"\u0000\u0000\u0124\u0128\u0003Q\'\u0000\u0125\u0127\u0003U)\u0000\u0126"+
		"\u0125\u0001\u0000\u0000\u0000\u0127\u012a\u0001\u0000\u0000\u0000\u0128"+
		"\u0126\u0001\u0000\u0000\u0000\u0128\u0129\u0001\u0000\u0000\u0000\u0129"+
		"X\u0001\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012b\u012f"+
		"\u0003S(\u0000\u012c\u012e\u0003U)\u0000\u012d\u012c\u0001\u0000\u0000"+
		"\u0000\u012e\u0131\u0001\u0000\u0000\u0000\u012f\u012d\u0001\u0000\u0000"+
		"\u0000\u012f\u0130\u0001\u0000\u0000\u0000\u0130Z\u0001\u0000\u0000\u0000"+
		"\u0131\u012f\u0001\u0000\u0000\u0000\u0132\u0136\u0003Q\'\u0000\u0133"+
		"\u0135\u0003S(\u0000\u0134\u0133\u0001\u0000\u0000\u0000\u0135\u0138\u0001"+
		"\u0000\u0000\u0000\u0136\u0134\u0001\u0000\u0000\u0000\u0136\u0137\u0001"+
		"\u0000\u0000\u0000\u0137\\\u0001\u0000\u0000\u0000\u0138\u0136\u0001\u0000"+
		"\u0000\u0000\u0139\u013d\u0003S(\u0000\u013a\u013c\u0003S(\u0000\u013b"+
		"\u013a\u0001\u0000\u0000\u0000\u013c\u013f\u0001\u0000\u0000\u0000\u013d"+
		"\u013b\u0001\u0000\u0000\u0000\u013d\u013e\u0001\u0000\u0000\u0000\u013e"+
		"^\u0001\u0000\u0000\u0000\u013f\u013d\u0001\u0000\u0000\u0000\u0140\u0143"+
		"\u0003\u001b\f\u0000\u0141\u0143\u0003\u001d\r\u0000\u0142\u0140\u0001"+
		"\u0000\u0000\u0000\u0142\u0141\u0001\u0000\u0000\u0000\u0143`\u0001\u0000"+
		"\u0000\u0000\u0144\u0148\u0003#\u0010\u0000\u0145\u0147\u0003K$\u0000"+
		"\u0146\u0145\u0001\u0000\u0000\u0000\u0147\u014a\u0001\u0000\u0000\u0000"+
		"\u0148\u0146\u0001\u0000\u0000\u0000\u0148\u0149\u0001\u0000\u0000\u0000"+
		"\u0149\u014b\u0001\u0000\u0000\u0000\u014a\u0148\u0001\u0000\u0000\u0000"+
		"\u014b\u014c\u0003#\u0010\u0000\u014cb\u0001\u0000\u0000\u0000\u014d\u014e"+
		"\u0003%\u0011\u0000\u014e\u014f\u0003K$\u0000\u014f\u0150\u0003%\u0011"+
		"\u0000\u0150d\u0001\u0000\u0000\u0000\u0151\u0153\u0003?\u001e\u0000\u0152"+
		"\u0151\u0001\u0000\u0000\u0000\u0152\u0153\u0001\u0000\u0000\u0000\u0153"+
		"\u0154\u0001\u0000\u0000\u0000\u0154\u0155\u0003M%\u0000\u0155\u0156\u0003"+
		"\'\u0012\u0000\u0156\u0157\u0003M%\u0000\u0157f\u0001\u0000\u0000\u0000"+
		"\u0158\u015a\u0003?\u001e\u0000\u0159\u0158\u0001\u0000\u0000\u0000\u0159"+
		"\u015a\u0001\u0000\u0000\u0000\u015a\u015b\u0001\u0000\u0000\u0000\u015b"+
		"\u015c\u0003M%\u0000\u015ch\u0001\u0000\u0000\u0000\u015d\u015f\u0007"+
		"\u0003\u0000\u0000\u015e\u015d\u0001\u0000\u0000\u0000\u015f\u0160\u0001"+
		"\u0000\u0000\u0000\u0160\u015e\u0001\u0000\u0000\u0000\u0160\u0161\u0001"+
		"\u0000\u0000\u0000\u0161\u0162\u0001\u0000\u0000\u0000\u0162\u0163\u0006"+
		"3\u0002\u0000\u0163j\u0001\u0000\u0000\u0000\u0164\u016a\u0003W*\u0000"+
		"\u0165\u0166\u0003o6\u0000\u0166\u0167\u0003W*\u0000\u0167\u0169\u0001"+
		"\u0000\u0000\u0000\u0168\u0165\u0001\u0000\u0000\u0000\u0169\u016c\u0001"+
		"\u0000\u0000\u0000\u016a\u0168\u0001\u0000\u0000\u0000\u016a\u016b\u0001"+
		"\u0000\u0000\u0000\u016bl\u0001\u0000\u0000\u0000\u016c\u016a\u0001\u0000"+
		"\u0000\u0000\u016d\u0173\u0003Y+\u0000\u016e\u016f\u0003o6\u0000\u016f"+
		"\u0170\u0003Y+\u0000\u0170\u0172\u0001\u0000\u0000\u0000\u0171\u016e\u0001"+
		"\u0000\u0000\u0000\u0172\u0175\u0001\u0000\u0000\u0000\u0173\u0171\u0001"+
		"\u0000\u0000\u0000\u0173\u0174\u0001\u0000\u0000\u0000\u0174\u0176\u0001"+
		"\u0000\u0000\u0000\u0175\u0173\u0001\u0000\u0000\u0000\u0176\u0177\u0006"+
		"5\u0003\u0000\u0177n\u0001\u0000\u0000\u0000\u0178\u017a\u0007\u0003\u0000"+
		"\u0000\u0179\u0178\u0001\u0000\u0000\u0000\u017a\u017b\u0001\u0000\u0000"+
		"\u0000\u017b\u0179\u0001\u0000\u0000\u0000\u017b\u017c\u0001\u0000\u0000"+
		"\u0000\u017cp\u0001\u0000\u0000\u0000\u017d\u017e\u0003[,\u0000\u017e"+
		"\u017f\u0001\u0000\u0000\u0000\u017f\u0180\u00067\u0003\u0000\u0180r\u0001"+
		"\u0000\u0000\u0000\u0181\u0187\u0003[,\u0000\u0182\u0183\u0003\'\u0012"+
		"\u0000\u0183\u0184\u0003[,\u0000\u0184\u0186\u0001\u0000\u0000\u0000\u0185"+
		"\u0182\u0001\u0000\u0000\u0000\u0186\u0189\u0001\u0000\u0000\u0000\u0187"+
		"\u0185\u0001\u0000\u0000\u0000\u0187\u0188\u0001\u0000\u0000\u0000\u0188"+
		"\u018a\u0001\u0000\u0000\u0000\u0189\u0187\u0001\u0000\u0000\u0000\u018a"+
		"\u018b\u00068\u0003\u0000\u018bt\u0001\u0000\u0000\u0000\u018c\u018d\u0003"+
		"]-\u0000\u018d\u018e\u0001\u0000\u0000\u0000\u018e\u018f\u00069\u0003"+
		"\u0000\u018fv\u0001\u0000\u0000\u0000\u0190\u0196\u0003]-\u0000\u0191"+
		"\u0192\u0003\'\u0012\u0000\u0192\u0193\u0003]-\u0000\u0193\u0195\u0001"+
		"\u0000\u0000\u0000\u0194\u0191\u0001\u0000\u0000\u0000\u0195\u0198\u0001"+
		"\u0000\u0000\u0000\u0196\u0194\u0001\u0000\u0000\u0000\u0196\u0197\u0001"+
		"\u0000\u0000\u0000\u0197\u0199\u0001\u0000\u0000\u0000\u0198\u0196\u0001"+
		"\u0000\u0000\u0000\u0199\u019a\u0006:\u0003\u0000\u019ax\u0001\u0000\u0000"+
		"\u0000\u0014\u0000\u0001\u0002\u0111\u0116\u0122\u0128\u012f\u0136\u013d"+
		"\u0142\u0148\u0152\u0159\u0160\u016a\u0173\u017b\u0187\u0196\u0004\u0005"+
		"\u0001\u0000\u0005\u0002\u0000\u0006\u0000\u0000\u0004\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}