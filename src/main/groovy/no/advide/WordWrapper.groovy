package no.advide

import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.text.WordUtils

class WordWrapper {

  DocumentFragment fragment
  int width = 80

  WordWrapper(DocumentFragment documentFragment) {
    fragment = documentFragment
  }

  void setWidth(int width) {
    this.width = width
  }

  void justify() {
    if (!alreadyJustified()) {
      concatenateToOneLine()
      wrapWordsInLine(0)
    }
  }

  boolean alreadyJustified() {
    fragment.lines == wrapWords(fragment.lines.join(" "))
  }

  private def concatenateToOneLine() {
    while (fragment.length > 1) {
      addSpaceBetweenLinesAsNewlineReplacement(0)
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

  private def addSpaceBetweenLinesAsNewlineReplacement(int y) {
    if (noWhiteSpaceAtEnd(y) || cursorAtEndOfLine(y)) fragment.appendTo(y, " ")
  }

  private boolean cursorAtEndOfLine(int y) {
    fragment.cursor == [x: fragment.lines[y].size(), y: y]
  }

  private boolean noWhiteSpaceAtEnd(int y) {
    return !fragment.lines[y].endsWith(" ")
  }

  private List<String> wrapWords(String string) {
    if (string.startsWith(" ")) throw new UnsupportedOperationException("word wrapper does not behave nicely on strings starting with whitespace")
    StringUtils.splitPreserveAllTokens(WordUtils.wrap(string, width), '\n')
  }
}
