package no.advide.commands

import no.advide.DocumentFragment
import no.advide.RoomNumber

class RemoveAlternativeCommand extends Command {

  static def matches(DocumentFragment fragment) {
    fragment.lines.first() =~ /^#\d+$/
  }

  static int numMatchingLines(DocumentFragment fragment) {
    1
  }

  RemoveAlternativeCommand(DocumentFragment fragment) {
    super(fragment)
    if (fragment.length != 1) throw new IllegalArgumentException("takes 1 line");
  }

  @Override
  List<RoomNumber> getRoomNumbers() {
    return [ new RoomNumber(
        number: fragment.lines.first().substring(1).toInteger(),
        position: fragment.translate([x:1, y:0])
    ) ]
  }


}
