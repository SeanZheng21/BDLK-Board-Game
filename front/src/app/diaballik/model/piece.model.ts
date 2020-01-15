import { Player } from './player.model';

export class Piece {

    private hasBall: boolean;
    private player: Player;

    constructor(hb: boolean, owner: Player) {
        this.hasBall = hb;
        this.player = owner;
    }

    public getHasBall(): boolean {
        return this.hasBall;
    }

    public getOwner(): Player {
        return this.player;
    }
}
