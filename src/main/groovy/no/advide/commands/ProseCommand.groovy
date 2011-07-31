package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.FormattedLine
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils

class ProseCommand extends Command {

  DocumentFragment fragment
  int width = 80
  List<String> lines

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^[a-zA-ZæøåÆØÅ]/
  }

  static Object numMatchingLines(List<String> strings, int fromIndex) {
    int index = fromIndex
    while (index < strings.size() && matches(strings, index)) index++
    index - fromIndex
  }

  ProseCommand(DocumentFragment fragment) {
    this.fragment = fragment
    lines = rewrapInput()
  }

  void setWidth(int width) {
    this.width = width
    lines = rewrapInput() // burde være mulig å bli kvitt denne
  }

  @Override
  void updateDocument() {
    concatenateToOneLine_orMaybeTwo()
    if (fragment.length == 2) wrapWordsInLine(1)
    wrapWordsInLine(0)
  }

  private def concatenateToOneLine_orMaybeTwo() {
    while (fragment.length > 1) {
      if (cursorIsAtStartOfSecondLine()) { concatenateToTwoLinesInstead(); break }
      ensureSpaceBetweenLines(0)
      fragment.mergeLineWithPrevious(1)
    }
  }

  private def wrapWordsInLine(int y) {
    def lines = wrapWords(fragment.lines[y])
    for (int i = 0; i < lines.size() - 1; i++) {
      fragment.splitAt(lines[i].size() + 1, y + i)
      fragment.chop(y + i)
    }
  }

  private def concatenateToTwoLinesInstead() {
    while (fragment.length > 2) {
      ensureSpaceBetweenLines(1)
      fragment.mergeLineWithPrevious(2)
    }
  }

  private boolean cursorIsAtStartOfSecondLine() {
    return fragment.cursor && fragment.cursor.x == 0 && fragment.cursor.y == 1
  }

  private def ensureSpaceBetweenLines(int y) {
    fragment.appendTo(y, " ")
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    fragment.lines.collect { new FormattedLine(text: it, color: Color.black)}
  }

  @Override
  List<String> toNewScript() { // todo: denne må dø -> funker ikke med newScript uten å kunne endre document
    lines                      // og document kan ikke endres under parsing -> så kan ikke gjøres i constructor
  }                            // evnt kan den kjøre updateDocument først, men trengs den til noe?

  private List<String> rewrapInput() {
    StringUtils.splitPreserveAllTokens(WordUtils.wrap(fragment.lines.join(" "), width), '\n')
  }

  private List<String> wrapWords(String string) {
    StringUtils.splitPreserveAllTokens(WordUtils.wrap(string, width), '\n')
  }

}
