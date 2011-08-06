package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment
import no.advide.WordWrapper

class ProseCommand extends Command {

  static def matches(DocumentFragment fragment) {
    startsWithProseIndicatingChar(fragment.lines.first())
  }

  static int numMatchingLines(DocumentFragment fragment) {
    for (int i = 0; i < fragment.length; i++) {
      if (!startsWithProseIndicatingChar(fragment.lines[i])) return i
      if (i > 0 && fragment.cursor && fragment.cursor.y == i) return i
    }
    return fragment.length
  }

  ProseCommand(fragment) {
    super(fragment)
  }

  @Override
  Color getColor() {
    Color.black
  }

  @Override
  void justifyProse(int width) {
    new WordWrapper(fragment, width).justify()
  }

  private static def startsWithProseIndicatingChar(String line) {
    line =~ /^[0-9a-zA-ZæøåÆØÅ"]/
  }

}
