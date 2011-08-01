package no.advide.commands

import no.advide.DocumentFragment

class RemoveAlternativeCommand extends Command {

  static def matches(List<String> strings, int fromIndex) {
    strings[fromIndex] =~ /^#\d+$/
  }

  static int numMatchingLines(Object lines, Object fromIndex) {
    1
  }

  RemoveAlternativeCommand(DocumentFragment fragment) {
    super(fragment)
    if (fragment.length != 1) throw new IllegalArgumentException("takes 1 line");
  }

}
