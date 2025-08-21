package org.json;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class Test {
    public static void main(String[] strArr) throws JSONException {
        Exception e;
        JSONArray jSONArray;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        Exception e2;
        JSONString jSONString = new JSONString("A beany object", 42.0d, true) { // from class: org.json.Test.1Obj
            public boolean aBoolean;
            public double aNumber;
            public String aString;

            {
                this.aString = str;
                this.aNumber = d;
                this.aBoolean = z;
            }

            public String getBENT() {
                return "All uppercase key";
            }

            public double getNumber() {
                return this.aNumber;
            }

            public String getString() {
                return this.aString;
            }

            public String getX() {
                return "x";
            }

            public boolean isBoolean() {
                return this.aBoolean;
            }

            @Override // org.json.JSONString
            public String toJSONString() {
                return "{" + JSONObject.quote(this.aString) + ":" + JSONObject.doubleToString(this.aNumber) + "}";
            }

            public String toString() {
                return getString() + " " + getNumber() + " " + isBoolean() + "." + getBENT() + " " + getX();
            }
        };
        try {
            System.out.println(XML.toJSONObject("<![CDATA[This is a collection of test patterns and examples for org.json.]]>  Ignore the stuff past the end.  ").toString());
            JSONObject jSONObject3 = new JSONObject("{     \"list of lists\" : [         [1, 2, 3],         [4, 5, 6],     ] }");
            System.out.println(jSONObject3.toString(4));
            System.out.println(XML.toString(jSONObject3));
            System.out.println(XML.toJSONObject("<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ").toString(4));
            System.out.println();
            JSONObject jSONObject4 = JSONML.toJSONObject("<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ");
            System.out.println(jSONObject4.toString());
            System.out.println(JSONML.toString(jSONObject4));
            System.out.println();
            JSONArray jSONArray2 = JSONML.toJSONArray("<recipe name=\"bread\" prep_time=\"5 mins\" cook_time=\"3 hours\"> <title>Basic bread</title> <ingredient amount=\"8\" unit=\"dL\">Flour</ingredient> <ingredient amount=\"10\" unit=\"grams\">Yeast</ingredient> <ingredient amount=\"4\" unit=\"dL\" state=\"warm\">Water</ingredient> <ingredient amount=\"1\" unit=\"teaspoon\">Salt</ingredient> <instructions> <step>Mix all ingredients together.</step> <step>Knead thoroughly.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Knead again.</step> <step>Place in a bread baking tin.</step> <step>Cover with a cloth, and leave for one hour in warm room.</step> <step>Bake in the oven at 180(degrees)C for 30 minutes.</step> </instructions> </recipe> ");
            System.out.println(jSONArray2.toString(4));
            System.out.println(JSONML.toString(jSONArray2));
            System.out.println();
            JSONObject jSONObject5 = JSONML.toJSONObject("<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>");
            System.out.println(jSONObject5.toString(4));
            System.out.println(JSONML.toString(jSONObject5));
            System.out.println();
            JSONArray jSONArray3 = JSONML.toJSONArray("<div id=\"demo\" class=\"JSONML\"><p>JSONML is a transformation between <b>JSON</b> and <b>XML</b> that preserves ordering of document features.</p><p>JSONML can work with JSON arrays or JSON objects.</p><p>Three<br/>little<br/>words</p></div>");
            System.out.println(jSONArray3.toString(4));
            System.out.println(JSONML.toString(jSONArray3));
            System.out.println();
            System.out.println(XML.toJSONObject("<person created=\"2006-11-11T19:23\" modified=\"2006-12-31T23:59\">\n <firstName>Robert</firstName>\n <lastName>Smith</lastName>\n <address type=\"home\">\n <street>12345 Sixth Ave</street>\n <city>Anytown</city>\n <state>CA</state>\n <postalCode>98765-4321</postalCode>\n </address>\n </person>").toString(4));
            System.out.println(new JSONObject(jSONString).toString());
            System.out.println(new JSONObject("{ \"entity\": { \"imageURL\": \"\", \"name\": \"IXXXXXXXXXXXXX\", \"id\": 12336, \"ratingCount\": null, \"averageRating\": null } }").toString(2));
            System.out.println(new JSONStringer().object().key("single").value("MARIE HAA'S").key("Johnny").value("MARIE HAA\\'S").key("foo").value("bar").key("baz").array().object().key("quux").value("Thanks, Josh!").endObject().endArray().key("obj keys").value(JSONObject.getNames(jSONString)).endObject().toString());
            System.out.println(new JSONStringer().object().key("a").array().array().array().value("b").endArray().endArray().endArray().endObject().toString());
            JSONStringer jSONStringer = new JSONStringer();
            jSONStringer.array();
            jSONStringer.value(1L);
            jSONStringer.array();
            jSONStringer.value((Object) null);
            jSONStringer.array();
            jSONStringer.object();
            jSONStringer.key("empty-array").array().endArray();
            jSONStringer.key("answer").value(42L);
            jSONStringer.key("null").value((Object) null);
            jSONStringer.key("false").value(false);
            jSONStringer.key("true").value(true);
            jSONStringer.key("big").value(1.23456789E96d);
            jSONStringer.key("small").value(1.23456789E-80d);
            jSONStringer.key("empty-object").object().endObject();
            jSONStringer.key("long");
            jSONStringer.value(Long.MAX_VALUE);
            jSONStringer.endObject();
            jSONStringer.value("two");
            jSONStringer.endArray();
            jSONStringer.value(true);
            jSONStringer.endArray();
            jSONStringer.value(98.6d);
            jSONStringer.value(-100.0d);
            jSONStringer.object();
            jSONStringer.endObject();
            jSONStringer.object();
            jSONStringer.key("one");
            jSONStringer.value(1.0d);
            jSONStringer.endObject();
            jSONStringer.value(jSONString);
            jSONStringer.endArray();
            System.out.println(jSONStringer.toString());
            System.out.println(new JSONArray(jSONStringer.toString()).toString(4));
            System.out.println(new JSONArray(new int[]{1, 2, 3}).toString());
            JSONObject jSONObject6 = new JSONObject(jSONString, new String[]{"aString", "aNumber", "aBoolean"});
            jSONObject6.put("Testing JSONString interface", jSONString);
            System.out.println(jSONObject6.toString(4));
            JSONObject jSONObject7 = new JSONObject("{slashes: '///', closetag: '</script>', backslash:'\\\\', ei: {quotes: '\"\\''},eo: {a: '\"quoted\"', b:\"don't\"}, quotes: [\"'\", '\"']}");
            System.out.println(jSONObject7.toString(2));
            System.out.println(XML.toString(jSONObject7));
            System.out.println("");
            JSONObject jSONObject8 = new JSONObject("{foo: [true, false,9876543210,    0.0, 1.00000001,  1.000000000001, 1.00000000000000001, .00000000000000001, 2.00, 0.1, 2e100, -32,[],{}, \"string\"],   to   : null, op : 'Good',ten:10} postfix comment");
            jSONObject8.put("String", "98.6");
            jSONObject8.put("JSONObject", new JSONObject());
            jSONObject8.put("JSONArray", new JSONArray());
            jSONObject8.put("int", 57);
            jSONObject8.put("double", 1.2345678901234568E29d);
            jSONObject8.put("true", true);
            jSONObject8.put("false", false);
            jSONObject8.put("null", JSONObject.NULL);
            jSONObject8.put("bool", "true");
            jSONObject8.put("zero", -0.0d);
            jSONObject8.put("\\u2028", "\u2028");
            jSONObject8.put("\\u2029", "\u2029");
            JSONArray jSONArray4 = jSONObject8.getJSONArray("foo");
            jSONArray4.put(666);
            jSONArray4.put(2001.99d);
            jSONArray4.put("so \"fine\".");
            jSONArray4.put("so <fine>.");
            jSONArray4.put(true);
            jSONArray4.put(false);
            jSONArray4.put(new JSONArray());
            jSONArray4.put(new JSONObject());
            jSONObject8.put("keys", JSONObject.getNames(jSONObject8));
            System.out.println(jSONObject8.toString(4));
            System.out.println(XML.toString(jSONObject8));
            System.out.println("String: " + jSONObject8.getDouble("String"));
            System.out.println("  bool: " + jSONObject8.getBoolean("bool"));
            System.out.println("    to: " + jSONObject8.getString("to"));
            System.out.println("  true: " + jSONObject8.getString("true"));
            System.out.println("   foo: " + jSONObject8.getJSONArray("foo"));
            System.out.println("    op: " + jSONObject8.getString("op"));
            System.out.println("   ten: " + jSONObject8.getInt("ten"));
            System.out.println("  oops: " + jSONObject8.optBoolean("oops"));
            JSONObject jSONObject9 = XML.toJSONObject("<xml one = 1 two=' \"2\" '><five></five>First \t&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>");
            System.out.println(jSONObject9.toString(2));
            System.out.println(XML.toString(jSONObject9));
            System.out.println("");
            JSONArray jSONArray5 = JSONML.toJSONArray("<xml one = 1 two=' \"2\" '><five></five>First \t&lt;content&gt;<five></five> This is \"content\". <three>  3  </three>JSON does not preserve the sequencing of elements and contents.<three>  III  </three>  <three>  T H R E E</three><four/>Content text is an implied structure in XML. <six content=\"6\"/>JSON does not have implied structure:<seven>7</seven>everything is explicit.<![CDATA[CDATA blocks<are><supported>!]]></xml>");
            System.out.println(jSONArray5.toString(4));
            System.out.println(JSONML.toString(jSONArray5));
            System.out.println("");
            JSONArray jSONArray6 = JSONML.toJSONArray("<xml do='0'>uno<a re='1' mi='2'>dos<b fa='3'/>tres<c>true</c>quatro</a>cinqo<d>seis<e/></d></xml>");
            System.out.println(jSONArray6.toString(4));
            System.out.println(JSONML.toString(jSONArray6));
            System.out.println("");
            JSONObject jSONObject10 = XML.toJSONObject("<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>");
            System.out.println(jSONObject10.toString(2));
            System.out.println(XML.toString(jSONObject10));
            System.out.println("");
            JSONArray jSONArray7 = JSONML.toJSONArray("<mapping><empty/>   <class name = \"Customer\">      <field name = \"ID\" type = \"string\">         <bind-xml name=\"ID\" node=\"attribute\"/>      </field>      <field name = \"FirstName\" type = \"FirstName\"/>      <field name = \"MI\" type = \"MI\"/>      <field name = \"LastName\" type = \"LastName\"/>   </class>   <class name = \"FirstName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"MI\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class>   <class name = \"LastName\">      <field name = \"text\">         <bind-xml name = \"text\" node = \"text\"/>      </field>   </class></mapping>");
            System.out.println(jSONArray7.toString(4));
            System.out.println(JSONML.toString(jSONArray7));
            System.out.println("");
            JSONObject jSONObject11 = XML.toJSONObject("<?xml version=\"1.0\" ?><Book Author=\"Anonymous\"><Title>Sample Book</Title><Chapter id=\"1\">This is chapter 1. It is not very long or interesting.</Chapter><Chapter id=\"2\">This is chapter 2. Although it is longer than chapter 1, it is not any more interesting.</Chapter></Book>");
            System.out.println(jSONObject11.toString(2));
            System.out.println(XML.toString(jSONObject11));
            System.out.println("");
            JSONObject jSONObject12 = XML.toJSONObject("<!DOCTYPE bCard 'http://www.cs.caltech.edu/~adam/schemas/bCard'><bCard><?xml default bCard        firstname = ''        lastname  = '' company   = '' email = '' homepage  = ''?><bCard        firstname = 'Rohit'        lastname  = 'Khare'        company   = 'MCI'        email     = 'khare@mci.net'        homepage  = 'http://pest.w3.org/'/><bCard        firstname = 'Adam'        lastname  = 'Rifkin'        company   = 'Caltech Infospheres Project'        email     = 'adam@cs.caltech.edu'        homepage  = 'http://www.cs.caltech.edu/~adam/'/></bCard>");
            System.out.println(jSONObject12.toString(2));
            System.out.println(XML.toString(jSONObject12));
            System.out.println("");
            JSONObject jSONObject13 = XML.toJSONObject("<?xml version=\"1.0\"?><customer>    <firstName>        <text>Fred</text>    </firstName>    <ID>fbs0001</ID>    <lastName> <text>Scerbo</text>    </lastName>    <MI>        <text>B</text>    </MI></customer>");
            System.out.println(jSONObject13.toString(2));
            System.out.println(XML.toString(jSONObject13));
            System.out.println("");
            JSONObject jSONObject14 = XML.toJSONObject("<!ENTITY tp-address PUBLIC '-//ABC University::Special Collections Library//TEXT (titlepage: name and address)//EN' 'tpspcoll.sgm'><list type='simple'><head>Repository Address </head><item>Special Collections Library</item><item>ABC University</item><item>Main Library, 40 Circle Drive</item><item>Ourtown, Pennsylvania</item><item>17654 USA</item></list>");
            System.out.println(jSONObject14.toString());
            System.out.println(XML.toString(jSONObject14));
            System.out.println("");
            JSONObject jSONObject15 = XML.toJSONObject("<test intertag status=ok><empty/>deluxe<blip sweet=true>&amp;&quot;toot&quot;&toot;&#x41;</blip><x>eks</x><w>bonus</w><w>bonus2</w></test>");
            System.out.println(jSONObject15.toString(2));
            System.out.println(XML.toString(jSONObject15));
            System.out.println("");
            JSONObject jSONObject16 = HTTP.toJSONObject("GET / HTTP/1.0\nAccept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*\nAccept-Language: en-us\nUser-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows 98; Win 9x 4.90; T312461; Q312461)\nHost: www.nokko.com\nConnection: keep-alive\nAccept-encoding: gzip, deflate\n");
            System.out.println(jSONObject16.toString(2));
            System.out.println(HTTP.toString(jSONObject16));
            System.out.println("");
            JSONObject jSONObject17 = HTTP.toJSONObject("HTTP/1.1 200 Oki Doki\nDate: Sun, 26 May 2002 17:38:52 GMT\nServer: Apache/1.3.23 (Unix) mod_perl/1.26\nKeep-Alive: timeout=15, max=100\nConnection: Keep-Alive\nTransfer-Encoding: chunked\nContent-Type: text/html\n");
            System.out.println(jSONObject17.toString(2));
            System.out.println(HTTP.toString(jSONObject17));
            System.out.println("");
            JSONObject jSONObject18 = new JSONObject("{nix: null, nux: false, null: 'null', 'Request-URI': '/', Method: 'GET', 'HTTP-Version': 'HTTP/1.0'}");
            System.out.println(jSONObject18.toString(2));
            System.out.println("isNull: " + jSONObject18.isNull("nix"));
            System.out.println("   has: " + jSONObject18.has("nix"));
            System.out.println(XML.toString(jSONObject18));
            System.out.println(HTTP.toString(jSONObject18));
            System.out.println("");
            JSONObject jSONObject19 = XML.toJSONObject("<?xml version='1.0' encoding='UTF-8'?>\n\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"><SOAP-ENV:Body><ns1:doGoogleSearch xmlns:ns1=\"urn:GoogleSearch\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><key xsi:type=\"xsd:string\">GOOGLEKEY</key> <q xsi:type=\"xsd:string\">'+search+'</q> <start xsi:type=\"xsd:int\">0</start> <maxResults xsi:type=\"xsd:int\">10</maxResults> <filter xsi:type=\"xsd:boolean\">true</filter> <restrict xsi:type=\"xsd:string\"></restrict> <safeSearch xsi:type=\"xsd:boolean\">false</safeSearch> <lr xsi:type=\"xsd:string\"></lr> <ie xsi:type=\"xsd:string\">latin1</ie> <oe xsi:type=\"xsd:string\">latin1</oe></ns1:doGoogleSearch></SOAP-ENV:Body></SOAP-ENV:Envelope>");
            System.out.println(jSONObject19.toString(2));
            System.out.println(XML.toString(jSONObject19));
            System.out.println("");
            JSONObject jSONObject20 = new JSONObject("{Envelope: {Body: {\"ns1:doGoogleSearch\": {oe: \"latin1\", filter: true, q: \"'+search+'\", key: \"GOOGLEKEY\", maxResults: 10, \"SOAP-ENV:encodingStyle\": \"http://schemas.xmlsoap.org/soap/encoding/\", start: 0, ie: \"latin1\", safeSearch:false, \"xmlns:ns1\": \"urn:GoogleSearch\"}}}}");
            System.out.println(jSONObject20.toString(2));
            System.out.println(XML.toString(jSONObject20));
            System.out.println("");
            JSONObject jSONObject21 = CookieList.toJSONObject("  f%oo = b+l=ah  ; o;n%40e = t.wo ");
            System.out.println(jSONObject21.toString(2));
            System.out.println(CookieList.toString(jSONObject21));
            System.out.println("");
            JSONObject jSONObject22 = Cookie.toJSONObject("f%oo=blah; secure ;expires = April 24, 2002");
            System.out.println(jSONObject22.toString(2));
            System.out.println(Cookie.toString(jSONObject22));
            System.out.println("");
            System.out.println(new JSONObject("{script: 'It is not allowed in HTML to send a close script tag in a string<script>because it confuses browsers</script>so we insert a backslash before the /'}").toString());
            System.out.println("");
            JSONTokener jSONTokener = new JSONTokener("{op:'test', to:'session', pre:1}{op:'test', to:'session', pre:2}");
            JSONObject jSONObject23 = new JSONObject(jSONTokener);
            System.out.println(jSONObject23.toString());
            System.out.println("pre: " + jSONObject23.optInt("pre"));
            System.out.println((int) jSONTokener.skipTo('{'));
            System.out.println(new JSONObject(jSONTokener).toString());
            System.out.println("");
            JSONArray jSONArray8 = CDL.toJSONArray("No quotes, 'Single Quotes', \"Double Quotes\"\n1,'2',\"3\"\n,'It is \"good,\"', \"It works.\"\n\n");
            System.out.println(CDL.toString(jSONArray8));
            System.out.println("");
            System.out.println(jSONArray8.toString(4));
            System.out.println("");
            JSONArray jSONArray9 = new JSONArray(" [\"<escape>\", next is an implied null , , ok,] ");
            System.out.println(jSONArray9.toString());
            System.out.println("");
            System.out.println(XML.toString(jSONArray9));
            System.out.println("");
            JSONObject jSONObject24 = new JSONObject("{ fun => with non-standard forms ; forgiving => This package can be used to parse formats that are similar to but not stricting conforming to JSON; why=To make it easier to migrate existing data to JSON,one = [[1.00]]; uno=[[{1=>1}]];'+':+6e66 ;pluses=+++;empty = '' , 'double':0.666,true: TRUE, false: FALSE, null=NULL;[true] = [[!,@;*]]; string=>  o. k. ; \r oct=0666; hex=0x666; dec=666; o=0999; noh=0x0x}");
            System.out.println(jSONObject24.toString(4));
            System.out.println("");
            if (jSONObject24.getBoolean("true") && !jSONObject24.getBoolean("false")) {
                System.out.println("It's all good");
            }
            System.out.println("");
            JSONObject jSONObject25 = new JSONObject(jSONObject24, new String[]{"dec", "oct", "hex", "missing"});
            System.out.println(jSONObject25.toString(4));
            System.out.println("");
            System.out.println(new JSONStringer().array().value(jSONArray9).value(jSONObject25).endArray());
            JSONObject jSONObject26 = new JSONObject("{string: \"98.6\", long: 2147483648, int: 2147483647, longer: 9223372036854775807, double: 9223372036854775808}");
            System.out.println(jSONObject26.toString(4));
            System.out.println("\ngetInt");
            System.out.println("int    " + jSONObject26.getInt("int"));
            System.out.println("long   " + jSONObject26.getInt("long"));
            System.out.println("longer " + jSONObject26.getInt("longer"));
            System.out.println("double " + jSONObject26.getInt("double"));
            System.out.println("string " + jSONObject26.getInt("string"));
            System.out.println("\ngetLong");
            System.out.println("int    " + jSONObject26.getLong("int"));
            System.out.println("long   " + jSONObject26.getLong("long"));
            System.out.println("longer " + jSONObject26.getLong("longer"));
            System.out.println("double " + jSONObject26.getLong("double"));
            System.out.println("string " + jSONObject26.getLong("string"));
            System.out.println("\ngetDouble");
            System.out.println("int    " + jSONObject26.getDouble("int"));
            System.out.println("long   " + jSONObject26.getDouble("long"));
            System.out.println("longer " + jSONObject26.getDouble("longer"));
            System.out.println("double " + jSONObject26.getDouble("double"));
            System.out.println("string " + jSONObject26.getDouble("string"));
            jSONObject26.put("good sized", Long.MAX_VALUE);
            System.out.println(jSONObject26.toString(4));
            System.out.println(new JSONArray("[2147483647, 2147483648, 9223372036854775807, 9223372036854775808]").toString(4));
            System.out.println("\nKeys: ");
            Iterator itKeys = jSONObject26.keys();
            while (itKeys.hasNext()) {
                String str = (String) itKeys.next();
                System.out.println(str + ": " + jSONObject26.getString(str));
            }
            System.out.println("\naccumulate: ");
            JSONObject jSONObject27 = new JSONObject();
            jSONObject27.accumulate("stooge", "Curly");
            jSONObject27.accumulate("stooge", "Larry");
            jSONObject27.accumulate("stooge", "Moe");
            jSONObject27.getJSONArray("stooge").put(5, "Shemp");
            System.out.println(jSONObject27.toString(4));
            System.out.println("\nwrite:");
            System.out.println(jSONObject27.write(new StringWriter()));
            JSONObject jSONObject28 = XML.toJSONObject("<xml empty><a></a><a>1</a><a>22</a><a>333</a></xml>");
            System.out.println(jSONObject28.toString(4));
            System.out.println(XML.toString(jSONObject28));
            JSONObject jSONObject29 = XML.toJSONObject("<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>");
            System.out.println(jSONObject29.toString(4));
            System.out.println(XML.toString(jSONObject29));
            JSONArray jSONArray10 = JSONML.toJSONArray("<book><chapter>Content of the first chapter</chapter><chapter>Content of the second chapter      <chapter>Content of the first subchapter</chapter>      <chapter>Content of the second subchapter</chapter></chapter><chapter>Third Chapter</chapter></book>");
            System.out.println(jSONArray10.toString(4));
            System.out.println(JSONML.toString(jSONArray10));
            JSONObject jSONObject30 = new JSONObject((Map) null);
            JSONArray jSONArray11 = new JSONArray((Collection) null);
            jSONObject30.append("stooge", "Joe DeRita");
            jSONObject30.append("stooge", "Shemp");
            jSONObject30.accumulate("stooges", "Curly");
            jSONObject30.accumulate("stooges", "Larry");
            jSONObject30.accumulate("stooges", "Moe");
            jSONObject30.accumulate("stoogearray", jSONObject30.get("stooges"));
            jSONObject30.put("map", (Map) null);
            jSONObject30.put("collection", (Collection) null);
            jSONObject30.put("array", jSONArray11);
            jSONArray11.put((Map) null);
            jSONArray11.put((Collection) null);
            System.out.println(jSONObject30.toString(4));
            System.out.println(new JSONObject("{plist=Apple; AnimalSmells = { pig = piggish; lamb = lambish; worm = wormy; }; AnimalSounds = { pig = oink; lamb = baa; worm = baa;  Lisa = \"Why is the worm talking like a lamb?\" } ; AnimalColors = { pig = pink; lamb = black; worm = pink; } } ").toString(4));
            JSONArray jSONArray12 = new JSONArray(" (\"San Francisco\", \"New York\", \"Seoul\", \"London\", \"Seattle\", \"Shanghai\")");
            System.out.println(jSONArray12.toString());
            JSONObject jSONObject31 = XML.toJSONObject("<a ichi='1' ni='2'><b>The content of b</b> and <c san='3'>The content of c</c><d>do</d><e></e><d>re</d><f/><d>mi</d></a>");
            System.out.println(jSONObject31.toString(2));
            System.out.println(XML.toString(jSONObject31));
            System.out.println("");
            JSONArray jSONArray13 = JSONML.toJSONArray("<a ichi='1' ni='2'><b>The content of b</b> and <c san='3'>The content of c</c><d>do</d><e></e><d>re</d><f/><d>mi</d></a>");
            System.out.println(jSONArray13.toString(4));
            System.out.println(JSONML.toString(jSONArray13));
            System.out.println("");
            System.out.println("\nTesting Exceptions: ");
            System.out.print("Exception: ");
            try {
                jSONArray = new JSONArray();
                try {
                    jSONArray.put(Double.NEGATIVE_INFINITY);
                    jSONArray.put(Double.NaN);
                    System.out.println(jSONArray.toString());
                } catch (Exception e3) {
                    e = e3;
                    System.out.println(e);
                    System.out.print("Exception: ");
                    System.out.println(jSONObject31.getDouble("stooge"));
                    System.out.print("Exception: ");
                    System.out.println(jSONObject31.getDouble("howard"));
                    System.out.print("Exception: ");
                    System.out.println(jSONObject31.put((String) null, "howard"));
                    System.out.print("Exception: ");
                    System.out.println(jSONArray.getDouble(0));
                    System.out.print("Exception: ");
                    System.out.println(jSONArray.get(-1));
                    System.out.print("Exception: ");
                    System.out.println(jSONArray.put(Double.NaN));
                    System.out.print("Exception: ");
                    jSONObject = XML.toJSONObject("<a><b>    ");
                    System.out.print("Exception: ");
                    jSONObject = XML.toJSONObject("<a></b>    ");
                    System.out.print("Exception: ");
                    jSONObject = XML.toJSONObject("<a></a    ");
                    System.out.print("Exception: ");
                    System.out.println(new JSONArray(new Object()).toString());
                    System.out.print("Exception: ");
                    System.out.println(new JSONArray("[)").toString());
                    System.out.print("Exception: ");
                    System.out.println(JSONML.toJSONArray("<xml").toString(4));
                    System.out.print("Exception: ");
                    System.out.println(JSONML.toJSONArray("<right></wrong>").toString(4));
                    System.out.print("Exception: ");
                    jSONObject2 = new JSONObject("{\"koda\": true, \"koda\": true}");
                    System.out.println(jSONObject2.toString(4));
                    System.out.print("Exception: ");
                    new JSONStringer().object().key("bosanda").value("MARIE HAA'S").key("bosanda").value("MARIE HAA\\'S").endObject().toString();
                    System.out.println(jSONObject2.toString(4));
                    return;
                }
            } catch (Exception e4) {
                e = e4;
                jSONArray = jSONArray12;
            }
            System.out.print("Exception: ");
            try {
                System.out.println(jSONObject31.getDouble("stooge"));
            } catch (Exception e5) {
                System.out.println(e5);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(jSONObject31.getDouble("howard"));
            } catch (Exception e6) {
                System.out.println(e6);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(jSONObject31.put((String) null, "howard"));
            } catch (Exception e7) {
                System.out.println(e7);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(jSONArray.getDouble(0));
            } catch (Exception e8) {
                System.out.println(e8);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(jSONArray.get(-1));
            } catch (Exception e9) {
                System.out.println(e9);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(jSONArray.put(Double.NaN));
            } catch (Exception e10) {
                System.out.println(e10);
            }
            System.out.print("Exception: ");
            try {
                jSONObject = XML.toJSONObject("<a><b>    ");
            } catch (Exception e11) {
                System.out.println(e11);
                jSONObject = jSONObject31;
            }
            System.out.print("Exception: ");
            try {
                jSONObject = XML.toJSONObject("<a></b>    ");
            } catch (Exception e12) {
                System.out.println(e12);
            }
            System.out.print("Exception: ");
            try {
                jSONObject = XML.toJSONObject("<a></a    ");
            } catch (Exception e13) {
                System.out.println(e13);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(new JSONArray(new Object()).toString());
            } catch (Exception e14) {
                System.out.println(e14);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(new JSONArray("[)").toString());
            } catch (Exception e15) {
                System.out.println(e15);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(JSONML.toJSONArray("<xml").toString(4));
            } catch (Exception e16) {
                System.out.println(e16);
            }
            System.out.print("Exception: ");
            try {
                System.out.println(JSONML.toJSONArray("<right></wrong>").toString(4));
            } catch (Exception e17) {
                System.out.println(e17);
            }
            System.out.print("Exception: ");
            try {
                jSONObject2 = new JSONObject("{\"koda\": true, \"koda\": true}");
            } catch (Exception e18) {
                jSONObject2 = jSONObject;
                e2 = e18;
            }
            try {
                try {
                    System.out.println(jSONObject2.toString(4));
                } catch (Exception e19) {
                    e2 = e19;
                    System.out.println(e2);
                    System.out.print("Exception: ");
                    new JSONStringer().object().key("bosanda").value("MARIE HAA'S").key("bosanda").value("MARIE HAA\\'S").endObject().toString();
                    System.out.println(jSONObject2.toString(4));
                    return;
                }
                new JSONStringer().object().key("bosanda").value("MARIE HAA'S").key("bosanda").value("MARIE HAA\\'S").endObject().toString();
                System.out.println(jSONObject2.toString(4));
                return;
            } catch (Exception e20) {
                System.out.println(e20);
                return;
            }
            System.out.print("Exception: ");
        } catch (Exception e21) {
            System.out.println(e21.toString());
        }
    }
}
