import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { DiaballikGame } from '../diaballik/model/diaballikgame.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-starter',
  templateUrl: './starter.component.html',
  styleUrls: ['./starter.component.css']
})

export class StarterComponent implements OnInit {

  private nameOne: string;
  private nameTwo: string;
  private mode: string;
  private level: string;
  private white: string;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
    this.nameOne = '';
    this.nameTwo = '';
    this.mode = 'Human';
    this.level = 'Noob';
    this.white = 'one';
  }

  private start() {
    console.log(`Create game: player one: ${this.nameOne} player two: ${this.nameTwo} mode: ${this.mode} level: ${this.level}`);
    this.postGame(this.nameOne, this.nameTwo, this.mode, this.level, this.white)
        .subscribe(game => {
          this.handleReturnedGame(game);
    }, error => {
        console.log(error);
    });
  }

  private postGame(nOne: string, nTwo: string, md: string, lv: string, oIW: string): Observable<DiaballikGame> {
    console.log('About to post a game!' + `game/create/${nOne}/${nTwo}/${md === 'AI' ? lv : md}/${oIW === 'one' ? true : false}`);
    return this.http.post<DiaballikGame>(`game/create/${nOne}/${nTwo}/${md === 'AI' ? lv :
     md}/${oIW === 'one' ? true : false}`, { observe: 'response' });
  }

  private handleReturnedGame(game: DiaballikGame) {
    console.log(game);
    this.router.navigate(['/diaballik'], {state: {data: { game }}});
  }
}
