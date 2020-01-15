import { Component, OnInit } from '@angular/core';
import {DiaballikGame} from './model/diaballikgame.model';
import { Tile } from './model/tile.model';
import { Player } from './model/player.model';
import { Board } from './model/board.model';
import { Piece } from './model/piece.model';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-diaballik',
  templateUrl: './diaballik.component.html',
  styleUrls: ['./diaballik.component.css']
})
export class DiaballikComponent implements OnInit {
  private ongoing: boolean;
  private game: DiaballikGame;
  private selectedX: number;
  private selectedY: number;
  private selected: boolean;
  private actionCount: number;
  private turnCount: number;
  private selectedBtn: HTMLButtonElement;
  private invalidAction: boolean;

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.invalidAction = false;
    this.selected = false;
    this.selectedX = -1;
    this.selectedY = -1;
    this.actionCount = -1;
    this.turnCount = -1;
    this.ongoing = true;
    console.log('OMG, it works!!!');
    try {
      const d = history.state.data.game;
      console.log('Data from starter: ' + d);
      this.game = this.convertGame(d);
    } catch (error) {
      console.error('Can not get game data, redirecting to starter!');
      this.router.navigate(['/starter']);
    }
  }

  private displayTile(x: number, y: number): string {
    const tile: Tile = this.game.getBoard().getTile(x, y);
    const p: Piece = tile.getPiece();
    if (p === undefined) {
        // return `${x} : ${y}  `;
    } else {
        const owner: Player = p.getOwner();
        const isWhite: boolean = owner.equals(this.game.getWhitePlayer());
        if (p.getHasBall()) {
          return isWhite ? `\u25cb` : `\u26ab`;
      } else {
          return isWhite ? `\u25a1` : `\u25a0`;
      }
    }
  }

  private convertGame(jsonStr: any): DiaballikGame {
    // const response: any = JSON.parse(jsonStr);
    const response: any = jsonStr;
    const game: DiaballikGame = new DiaballikGame();
    game.setTurnCount(response.turnCount);
    game.setPlayerOne(new Player(response.playerOne.name, response.playerOne.colorWhite, response.playerOne.type));
    game.setPlayerTwo(new Player(response.playerTwo.name, response.playerTwo.colorWhite, response.playerTwo.type));
    game.setOngoing(response.ongoing);
    game.setActionCount(response.action);
    const board: Board = new Board(7);
    for (let i = 0; i < response.board.tiles.length; i++) {
        const element = response.board.tiles[i];
        if (element.hasOwnProperty('piece')) {
            const p = new Player(element.piece.player.name, element.piece.player.colorWhite, element.piece.player.type);
            const piece: Piece = new Piece(element.piece.hasBall, p);
            const tile: Tile = new Tile(element.position, piece);
            board.setTileIdx(tile, i);
        } else {
            const tile: Tile = new Tile(element.position, undefined);
            board.setTileIdx(tile, i);
        }
    }
    game.setBoard(board);
    this.turnCount = game.getTurnCount();
    this.actionCount = game.getActionCount();
    this.ongoing = game.getOngoing();
    return game;
  }

  private clickTile(event: Event, x: number, y: number) {
    if (!this.selected) {
      this.selected = true;
      this.selectedX = x;
      this.selectedY = y;
      const btn: HTMLButtonElement = event.target as HTMLButtonElement;
      this.selectedBtn = btn;
      btn.style.backgroundColor = 'blue';
      btn.style.color = 'white';
      // btn.style.borderColor = 'black';
      console.log(`Button clicked at ${x},${y} !`);
    } else if(x === this.selectedX && y === this.selectedY) {
      this.selected = false;
      this.selectedBtn.style.backgroundColor = 'white';
      this.selectedBtn.style.color = 'black';
    } else {
        // console.log('In post: ' + JSON.stringify(this.game));
        this.selectedBtn.style.backgroundColor = 'white';
        this.selectedBtn.style.color = 'black';
        this.postGame(this.selectedX, this.selectedY, x, y).subscribe(game => {
            this.handleGame(game);
            this.invalidAction = false;
        }, error => {
            console.log(error);
            this.invalidAction = true;
            this.selected = false;
        });
    }
  }

  private postGame(x1: number, y1: number, x2: number, y2: number): Observable<DiaballikGame>  {
    return this.http.put<DiaballikGame>(`game/action/${x1}/${y1}/${x2}/${y2}`, {}, {});
  }

  private handleGame(incomingGame: DiaballikGame) {
    console.log('Handle incoming game!');
    this.game = this.convertGame(incomingGame);
    this.cancelSelection();
}

  private next() {
      console.log('Next!');
      this.http.put<DiaballikGame>(`game/next`, {}, {}).subscribe(game => {
        this.handleGame(game);
    }, error => {
        console.log(error);
        this.cancelSelection();
    });
  }


  private undo() {
    return this.http.put<DiaballikGame>(`game/undo`, {}, {}).subscribe(game => {
        this.handleGame(game);
    }, error => {
        console.log(error);
        this.cancelSelection();
    });
}

private redo() {
    console.log('redo!');
    return this.http.put<DiaballikGame>(`game/redo`, {}, {}).subscribe(game => {
        this.handleGame(game);
    }, error => {
        this.cancelSelection();
        console.log(error);
    });
}

private cancelSelection() {
    this.selectedBtn.style.backgroundColor = 'white';
    this.selectedBtn.style.color = 'black';
    this.selected = false;

}

}


