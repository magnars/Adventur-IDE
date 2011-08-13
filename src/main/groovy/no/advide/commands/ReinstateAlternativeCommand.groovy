package no.advide.commands

import no.advide.DocumentFragment
import no.advide.RoomNumber

class ReinstateAlternativeCommand extends Command {

  static def matches(DocumentFragment fragment) {
    fragment.lines.first() =~ /^\*\d+$/
  }

  static int numMatchingLines(DocumentFragment fragment) {
    1
  }

  ReinstateAlternativeCommand(DocumentFragment fragment) {
    super(fragment)
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    return [ new RoomNumber(
        number: number,
        position: fragment.translate([x:1, y:0])
    ) ]
  }

  private int getNumber() {
    return fragment.lines.first().substring(1).toInteger()
  }

}
