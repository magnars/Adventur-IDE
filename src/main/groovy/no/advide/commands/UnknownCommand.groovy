package no.advide.commands

import java.awt.Color
import no.advide.DocumentFragment

class UnknownCommand extends Command {

  static boolean matches(Object lines, Object fromIndex) {
    true
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }

  UnknownCommand(DocumentFragment fragment) {
    super(fragment)
    if (fragment.length != 1) throw new IllegalArgumentException("takes 1 line");
  }

  @Override
  Color getColor() {
    Color.black
  }


}
