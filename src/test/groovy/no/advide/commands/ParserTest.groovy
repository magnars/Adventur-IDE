package no.advide.commands

class ParserTest extends GroovyTestCase {

    void test_no_lines() {
        assertTranslation([""], [])
    }

    void test_empty_line() {
        assertTranslation([""], [""])
    }

    void test_concatenate_plain_text() {
        assertTranslation(["Hei alle sammen"], ["Hei", "alle", "sammen"])
    }

//    void test_dont_concatenate_commands_with_text() {
//        assertTranslation(["#71", "Du drar sverdet"], ["#71", "Du", "drar sverdet"])
//    }

    private void assertTranslation(after, before) {
        assertEquals(after, Parser.translate(before))
    }

}
