/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package RoborallySpringBoot.RoboAPI.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum of the different commands there may be on a command card.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public enum Command {

    EMPTY(""),
    FORWARD("Fwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),
    U_TURN("U-Turn"),
    AGAIN("Again"),
    BACK("Move Back"),


    OPTION_LEFT_RIGHT("Left or Right", LEFT, RIGHT);

    final public String displayName;

    final private List<Command> options;

    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    public boolean isInteractive() {
        return !options.isEmpty();
    }

    public List<Command> getOptions() {
        return options;
    }

    public static Command fromString(String input) throws IllegalArgumentException{
        return switch (input) {
            case "Empty", "" -> Command.EMPTY;
            case "Fwd", "Forward", "forward", "fwd" -> Command.FORWARD;
            case "Turn Right", "turn right" -> Command.RIGHT;
            case "Turn Left","turn left" -> Command.LEFT;
            case "Fast Fwd","fast fwd", "Fast Forward", "fast forward" -> Command.FAST_FORWARD;
            case "Move Back", "Back", "Move back", "move back" -> Command.BACK;
            case "U-Turn", "u-turn", "Turn Around", "Turn around"->Command.U_TURN;
            case "Repeat", "Again", "again", "repeat"->Command.AGAIN;
            case "Left or right", "left or right", "L or R"-> Command.OPTION_LEFT_RIGHT;

            default -> throw new IllegalArgumentException("Invalid command");
        };
    }
        // existing code...

    public static boolean areValidCommands(List<String> input) {
        for (Command command : Command.values()) {
            for (String commandInput : input) {
                if (command.displayName.equalsIgnoreCase(commandInput)) {
                    //TODO check if this is reachable
                    return true;
                }
            }
        }
        return false;
    }
}

