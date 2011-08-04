package no.advide.commands

import no.advide.DocumentFragment
import no.advide.FormattedLine

class ConditionalCommand extends Command {

  ConditionalCommand(fragment) {
    super(fragment)
  }

  static boolean matches(DocumentFragment fragment) {
    fragment.lines.first().startsWith("[!]")
  }

  static int numMatchingLines(DocumentFragment fragment) {
    int size = fragment.lines.size()
    int numBlocks = 0
    for (int i = 1; i < size; i++) {
      if (fragment.lines[i] == "{") numBlocks++
      if (fragment.lines[i] == "}") numBlocks--
      if (numBlocks == 0) return i + 1
    }
    return size
  }

  @Override
  List<FormattedLine> getFormattedLines() {
    def lines = super.getFormattedLines()
    if (fragment.cursor) {
      lines.first().isEmbossedTop = true
      lines.each { it.isEmbossed = true }
      lines.last().isEmbossedBottom = true
    }
    lines
  }


}
