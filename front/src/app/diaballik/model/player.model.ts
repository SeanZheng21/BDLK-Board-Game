export class Player {

    private name: string;
    private colorWhite: boolean;
    private type: string;

    constructor(n: string, cw: boolean, type: string= 'humanPlayer') {
        this.name = n;
        this.colorWhite = cw;
        this.type = type;
    }

    public getName(): string {
        return this.name;
    }

    public isColorWhite(): boolean {
        return this.colorWhite;
    }

    public equals(p: Player): boolean {
        return (this.name === p.name) && (this.colorWhite === p.colorWhite);
    }
}
