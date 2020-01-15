import { Player } from './player.model';
import { Board } from './board.model';

export class DiaballikGame {

    private playerOne: Player;
    private playerTwo: Player;
    private ongoing: boolean;
    private board: Board;
    private mode: string;
    private turnCount: number;
    private action: number;

    // constructor(p1: string, p2: string, mode: string) {
    //     this.playerOne = new Player(p1, true);
    //     this.playerTwo = new Player(p2, false);
    //     this.ongoing = false;
    //     this.mode = mode;
    //     this.turnCount = 0;
    //     this.board = new Board(7);
    // }

    constructor() {}

    public getTurnCount(): number {
        return this.turnCount;
    }

    public setTurnCount(turnCount: number) {
        this.turnCount = turnCount;
    }

    public getActionCount(): number {
        return this.action;
    }

    public setActionCount(actionCOunt: number) {
        this.action = actionCOunt;
    }

    public getMode(): string {
        return this.mode;
    }

    public setMode(mode: string) {
        this.mode = mode;
    }

    public isOngoing(): boolean {
        return this.ongoing;
    }

    public setOngoing(ongoing: boolean) {
        this.ongoing = ongoing;
    }

    public getOngoing(): boolean {
        return this.ongoing;
    }

    public getBoard(): Board {
        return this.board;
    }

    public setBoard(board: Board) {
        this.board = board;
    }

    public getPlayerOne(): Player {
        return this.playerOne;
    }

    public setPlayerOne(playerOne: Player) {
        this.playerOne = playerOne;
    }

    public getPlayerTwo(): Player {
        return this.playerTwo;
    }

    public setPlayerTwo(playerTwo: Player) {
        this.playerTwo = playerTwo;
    }

    public getWhitePlayer(): Player {
        if (this.playerOne.isColorWhite()) {
            return this.playerOne;
        } else {
            return this.playerTwo;
        }
    }

    public getBlackPlayer(): Player {
        if (this.playerOne.isColorWhite()) {
            return this.playerTwo;
        } else {
            return this.playerOne;
        }
    }
}
