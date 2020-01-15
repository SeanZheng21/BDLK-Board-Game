import { Piece } from './piece.model';

export class Tile {
    private position: number;
    private piece: Piece | undefined;

    constructor(ps: number, p: Piece | undefined) {
        this.position = ps;
        this.piece = p;
    }

    public hasPiece(): boolean {
        return this.piece !== undefined;
    }

    public getPiece(): Piece {
        if (this.hasPiece()) {
            return this.piece;
        } else {
            return undefined;
        }
    }
}
