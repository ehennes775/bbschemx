package views.schematic.keys

val keymap = keymap {

    operation("Add Component") {
        key('a')
        key('c')
    }

    operation("Add Attribute") {
        key('a')
        key('a')
    }

    operation("Add Net") {
        key('a')
        key('n')
    }

    operation("Add Bus") {
        key('a')
        key('u')
    }

    operation("Add Text") {
        key('a')
        key('t')
    }

    operation("Add Line") {
        key('a')
        key('l')
    }

    operation("Add Path") {
        key('a')
        key('h')
    }

    operation("Add Box") {
        key('a')
        key('b')
    }

    operation("Add Circle") {
        key('a')
        key('i')
    }

    operation("Add Arc") {
        key('a')
        key('r')
    }

    operation("Add Pin") {
        key('a')
        key('p')
    }

    operation("Add Picture") {
        key('a')
        key('g')
    }

    operation("Select All") {
        key('a') /// add control
    }

    operation("Select All") {
        key('A') /// add control
    }

    operation("add Box") {
        key('B') /// add control
    }


    operation("Redraw") {
        key('v')
        key('r')
    }

    operation("Pan") {
        key('v')
        key('p')
    }

    operation("Zoom Box") {
        key('v')
        key('b')
    }

    operation("Zoom In") {
        key('v')
        key('i')
    }

    operation("Zoom In") {
        key('z')
    }

    operation("Zoom Extents") {
        key('v')
        key('e')
    }

    operation("Zoom Out") {
        key('Z')
    }

    operation("Zoom Out") {
        key('v')
        key('o')
    }
}

