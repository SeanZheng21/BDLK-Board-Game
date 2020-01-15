import { Tile } from './tile.model';
export class Board {

    readonly DIM: number;
    private tiles: Tile[];

    constructor(d: number) {
        this.DIM = d;
        this.tiles = [];
        for (let i = 0; i < d * d; i++) {
            const t: Tile = new Tile(i, undefined);
            this.tiles.push(t);
        }
    }

    public getTile(x: number, y: number): Tile {
        const idx = x + y * this.DIM;
        if (idx >= 0 && idx < this.DIM * this.DIM) {
            return this.tiles[idx];
        } else {
            console.error(`Can not get tile from ${x} : ${y}`);
            return undefined;
        }
    }

    public setTileXY(tile: Tile, x: number, y: number) {
        const idx = x + y * this.DIM;
        this.setTileIdx(tile, idx);
    }

    public setTileIdx(tile: Tile, pos: number) {
        if (pos >= 0 && pos < this.DIM * this.DIM) {
            this.tiles[pos] = tile;
        } else {
            console.error(`Can not get tile from ${pos}`);
        }
    }
}
