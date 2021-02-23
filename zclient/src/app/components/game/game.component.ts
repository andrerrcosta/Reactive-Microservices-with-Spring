import { WavePoint } from './../../utils/wave.module';
import { Component, Output, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Observable, Subject, Subscription } from 'rxjs';
import { finalize, take } from 'rxjs/operators';
import { getElement } from 'src/app/utils/dom.functions';
import { Pct, Px } from 'src/app/utils/formatters';
import { Percent } from 'src/app/utils/math.functions';
import { Undefined } from 'src/app/utils/optional';
import { WaveMapper } from '../../utils/wave.module';
import { SystemService } from './../../services/system.service';
import { Point } from './../../utils/point.model';

@Component({
  selector: 'app-game',
  templateUrl: 'game.component.html',
  styleUrls: ['./game.component.css'],
})

/**
 * I DO NOT ADVICE ANYONE TO CREATE A FRONT-END THIS WAY.
 * NOT TOO MUCH BY ITS LOGIC BUT FOR ITS READABILITY.
 *
 * THIS IS JUST AN EXAMPLE.
 *
 *
 *
 *
 *
 *
 *
 *
 */
export class GameComponent implements OnInit {
  engine: GameEngine;
  engineConfig: EngineConfig;
  start: boolean;
  end: boolean;
  mapper: WaveMapper;

  constructor(private system: SystemService, private router: Router) {
    this.engineConfig = new EngineConfig();
    this.engine = new GameEngine('game-box', this.engineConfig);
    this.start = false;
    this.end = false;
    this.mapper = new WaveMapper(system.currentUser.id);
  }

  ngOnInit() {
    this.setUpEngine();
  }

  play(): void {
    this.start = true;
    this.setUpEngine();
    this.engine.start((state: WavePoint) => {
      this.mapper.store(new Point(state.x, state.y, state.z), state.timestamp);
      if (state.z !== 0) {
        // console.warn('State:', state);

        this.system.storeWave(this.mapper.getWave(this.system.currentUser.id)).subscribe((res) => {
          console.log('Response', res);
          if (state.z === -1) {
            this.end = true;
          }
        });
        this.mapper.clear();
      }
    });
  }

  config(): void {
    this.start = false;
    this.end = false;
  }

  restart(): void {
    this.end = false;
    this.play();
  }

  quit(): void {
    this.start = false;
    this.end = false;
  }

  analyze(): void {
    this.router.navigate(['game-analysis']);
  }

  private setUpEngine(): void {
    this.engine = new GameEngine('game-box', this.engineConfig);
    this.engine.cheater.setUpBoard('game-box');
  }
}

/**
 * GAME-ENGINE
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

class GameEngine {
  private gameBox: HTMLElement | null;
  private size: Point;
  private id: string;
  private $tates: Subject<WavePoint>;
  private currentMousePosition: Point;
  private timestamp: number;

  public cheater: Cheater;
  public scores: number;
  public timeouts: number;
  public accuracy: string;
  public config: EngineConfig;

  constructor(id: string, config: EngineConfig) {
    this.gameBox = null;
    this.id = id;
    this.size = new Point(0, 0);
    this.config = config;
    this.scores = 0;
    this.timeouts = 0;
    this.accuracy = '100%';
    this.$tates = new Subject();
    this.currentMousePosition = new Point(0, 0, 0);
    this.timestamp = 0;
    this.cheater = new Cheater(config);
    this.clearBoard();

    window.addEventListener('keyup', (e) => this.cheatEvent(e));
  }

  start(observer: any) {
    this.$tates.subscribe(observer);
    this.setUp();
    this.createGameElementInterval().subscribe(() => this.renderGameElement());
  }

  clearBoard(): void {
    let board = getElement(this.id);
    if (board) board.innerHTML = '';
  }

  private cheatEvent(e: KeyboardEvent) {
    if (e.key === 'q') {
      this.cheater.enabled = !this.cheater.enabled;
    }
  }

  private setUp() {
    this.setGameBox();
    this.startTimer();
  }

  private renderGameElement(): void {
    console.log('Render');

    let e = new GameElement(Undefined(this.config.elementSize, 40));
    e.setPosition(this.getRandomPlace())
      .render(this.id, this.config.timeAlive)
      .subscribe((res: string) => {
        if (res) this.$Score();
        else this.$Timeout();
        this.accuracy = Pct(
          Percent(Number(this.scores) + Number(this.timeouts), Number(this.scores), 2)
        );
      });

    /**
     * This observer replaces the mouse event...
     */
    if (this.cheater.enabled) {
      this.cheater.cheat(e, (point: Point) => {
        this.$tates.next(new WavePoint(0, point, this.timestamp));
      });
    }
  }

  private setMouseEvent(e: MouseEvent) {
    if (!this.cheater.enabled) {
      this.currentMousePosition = new Point(e.clientX, e.clientY, 0);
      this.$tates.next(
        new WavePoint(0, this.currentMousePosition, this.timestamp)
      );
    }
  }

  private $Score() {
    this.scores++;
    this.$tates.next(
      new WavePoint(0, this.currentMousePosition.WithZ(1), this.timestamp)
    );
  }

  private $Timeout() {
    this.timeouts++;
    this.$tates.next(
      new WavePoint(0, this.currentMousePosition.WithZ(2), this.timestamp)
    );
  }

  private createGameElementInterval(): Observable<number> {
    return interval(this.config.timeBetween).pipe(
      take(Math.ceil(this.config.timeLimit / this.config.timeBetween)),
      finalize(() => this.finishGame())
    );
  }

  private getRandomPlace(): Point {
    return new Point(
      Math.floor(Math.random() * (this.size.x - this.config.elementSize)),
      Math.floor(Math.random() * (this.size.y - this.config.elementSize))
    );
  }

  private startTimer(): void {
    let $timer = interval(1).pipe(take(this.config.timeLimit));
    $timer.subscribe(
      (next) => (this.timestamp = next),
      (error) => console.error(error),
      () => this.die()
    );
  }

  private setGameBox(): void {
    this.gameBox = getElement(this.id);
    if (this.gameBox !== null) {
      this.gameBox.addEventListener('mousemove', (e) => this.setMouseEvent(e));
      this.size = new Point(
        this.gameBox.offsetHeight,
        this.gameBox.offsetWidth
      );
    }
  }

  private finishGame(complete?: any): void {
    setTimeout(() => {
      console.log('FINISHING THE GAME');
      this.$tates.next(
        new WavePoint(0, this.currentMousePosition.WithZ(-1), this.timestamp)
      );
      this.die();
    }, this.config.timeAlive + 1000);
  }

  private die(): void {
    this.timestamp = 0;
    this.$tates.complete();
    this.gameBox?.removeEventListener('mousemove', (e) =>
      this.setMouseEvent(e)
    );
    window.removeEventListener('keyup', (e) => this.cheatEvent(e));
  }

  get enabledCheater(): boolean {
    return this.cheater.enabled;
  }
}

/**
 * ENGINE-CONFIG
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

class EngineConfig {
  timeBetween: number;
  timeAlive: number;
  elementSize: number;
  timeLimit: number;

  constructor() {
    this.timeBetween = 1500;
    this.timeAlive = 1000;
    this.elementSize = 40;
    this.timeLimit = 5000;
  }

  console(): void {
    console.log('Starting Game');
    console.log('timeBetween', this.timeBetween);
    console.log('timeAlive', this.timeAlive);
    console.log('timeLimit', this.timeLimit);
    console.log('elementSize', this.elementSize);
  }
}

/**
 * GAME-ELEMENT
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

class GameElement {
  private element: HTMLElement;
  private $ubject: Subject<boolean>;
  private timeout: any;
  public position: Point;

  constructor(size?: number) {
    this.element = document.createElement('div');
    this.$ubject = new Subject();
    this.position = new Point(0, 0);
    this.setUp(Undefined(size, 40));
  }

  private setUp(size: number) {
    this.element.style.width = Px(size);
    this.element.style.height = Px(size);
    this.element.style.borderRadius = Pct(50);
    this.element.style.background = 'blue';
    this.element.style.position = 'absolute';
  }

  setPosition(pos: Point): GameElement {
    this.position = pos;
    this.element.style.top = Px(pos.x);
    this.element.style.left = Px(pos.y);
    return this;
  }

  render(boardId: string, timeAlive: number): GameElement {
    let board = getElement(boardId);
    board?.appendChild(this.element);
    let clicked = false;
    this.element.addEventListener('click', () => {
      this.element.style.background = 'red';
      clicked = true;
      setTimeout(() => {
        this.$ubject.next(true);
        this.element.outerHTML = '';
        this.die();
      }, 50);
    });

    this.timeout = setTimeout(() => {
      if (!clicked) {
        this.$ubject.next(false);
        this.element.style.background = 'grey';
        this.element.outerHTML = '';
        // this.element.removeEventListener('click', () => this.gameListener());
        this.die();
      }
    }, timeAlive);

    return this;
  }

  getElement(): HTMLElement {
    return this.element;
  }

  subscribe(observer: any) {
    return this.$ubject.subscribe(observer);
  }

  private die(): void {
    this.$ubject.complete();
    clearInterval(this.timeout);
  }
}

/**
 * This Cheater is far from ideal but this is what we have for now
 * Is impossible to change the cursor position with javascript
 * furthermore this is not a real job.
 *
 * CHEATER
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

class Cheater {
  enabled: boolean;
  engineConfig: EngineConfig;
  mousePosition: Point | undefined;
  currentGameElement: GameElement;
  $ubject: Subject<Point>;
  fakeCursor: HTMLImageElement;
  board: HTMLElement | null;

  constructor(engineConfig: EngineConfig) {
    this.engineConfig = engineConfig;
    this.enabled = false;
    this.currentGameElement = new GameElement();
    this.$ubject = new Subject();
    this.fakeCursor = document.createElement('img');
    this.fakeCursor.src = 'http://localhost:4200/assets/cursor001.png';
    this.fakeCursor.style.width = Px(20);
    this.fakeCursor.style.height = Px(25);
    this.fakeCursor.style.position = 'absolute';
    this.fakeCursor.style.zIndex = '9999';
    this.fakeCursor.style.display = 'none';
    this.board = null;
  }

  setUpBoard(id: string) {
    // console.log('Setting Up Board');

    this.board = getElement(id);
    if (this.board) {
      this.board.appendChild(this.fakeCursor);
    } else {
      console.error("Didn't found the board element");
    }
  }

  cheat(gameElement: GameElement, observer: any): Subscription {
    this.currentGameElement = gameElement;
    this.createLinearWave(this.currentGameElement.position);

    return this.$ubject.subscribe(observer);
  }

  private createLinearWave(target: Point): any {
    // console.log('Creating Linear Wave', target);

    let output: Point[] = [];

    this.fakeCursor.style.left = Px(this.cursor.x);
    this.fakeCursor.style.top = Px(this.cursor.y);

    if (this.board) {
      this.board.style.cursor = 'none';
      this.fakeCursor.style.display = 'unset';
      console.log(this.fakeCursor);

      let steps = 20;
      let cursor = new Point(this.cursor.x, this.cursor.y);
      // console.warn('Need to walk\nX:', (target.x - cursor.x), '\n', (target.y - cursor.y));
      let xStep = (target.x - cursor.x) / steps;
      let yStep = (target.y - cursor.y) / steps;

      this.animateCheat(xStep, yStep);
    } else {
      console.error("Board wasn't found");
    }
  }

  private animateCheat(xStep: number, yStep: number): void {
    let timer = 20;

    let i = 0;
    let k = setInterval(() => {
      this.fakeCursor.style.left = Px(Math.ceil(xStep * i));
      this.fakeCursor.style.top = Px(Math.ceil(yStep * i));

      if (i == 19) {
        this.currentGameElement.getElement().click();
        this.mousePosition = new Point(xStep * i, yStep * i);
        clearInterval(k);
        if (this.board) this.board.style.cursor = 'unset';
        this.fakeCursor.style.display = 'none';
        this.$ubject.next(new Point(xStep * i, yStep * i, 2));
      } else {
        this.$ubject.next(new Point(xStep * i, yStep * i, 0));
      }
      i++;
    }, timer);
  }

  private get cursor(): Point {
    return Undefined(this.mousePosition, this.boardCenter);
  }

  private get boardCenter(): Point {
    let x = this.board ? Math.floor(this.board.clientWidth / 2) : 0;
    let y = this.board ? Math.floor(this.board.clientHeight / 2) : 0;
    return new Point(x, y);
  }
}
