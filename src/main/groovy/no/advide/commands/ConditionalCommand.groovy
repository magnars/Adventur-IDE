package no.advide.commands

import no.advide.DocumentFragment

class ConditionalCommand extends BlockCommand {

  ConditionalCommand(fragment) {
    super(fragment)
  }

  static boolean matches(DocumentFragment fragment) {
    matchesOldForm(fragment) || matchesNewForm(fragment)
  }

  private static def matchesOldForm(DocumentFragment fragment) {
    fragment.lines.first().startsWith("[!]")
  }

  private static def matchesNewForm(DocumentFragment fragment) {
    fragment.lines.first().startsWith("? ")
  }

  static int numMatchingLines(DocumentFragment fragment) {
    if (matchesOldForm(fragment)) return numMatchingLinesOldForm(fragment)
    if (matchesNewForm(fragment)) return numMatchingLinesNewForm(fragment)
    0
  }

  @Override
  boolean isOldForm() {
    matchesOldForm(fragment)
  }

  @Override
  boolean isNewForm() {
    matchesNewForm(fragment)
  }

  @Override
  List<String> toNewStyle() {
    toNewStyle("? ${requirement}")
  }

  @Override
  List<String> toOldStyle() {
    toOldStyle("[!]${requirement}")
  }

  String getRequirement() {
    matchesNewForm(fragment) ? fragment.lines.first().substring(2) : fragment.lines.first().substring(3)
  }

}
