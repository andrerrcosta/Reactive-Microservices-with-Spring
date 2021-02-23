import { Injectable } from '@angular/core';
import { Subscription } from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { environment } from './../../environments/environment.prod';
import { SystemService } from './system.service';

@Injectable()
export class SystemStresserService {
  private $webSocket: WebSocketSubject<any>;

  constructor(private system: SystemService) {
    this.$webSocket = webSocket({
      url: environment.wsAnalyticsClient,
      deserializer: (msg) => msg,
    });
  }

  start(observer: any): Subscription {
    this.$webSocket.next({message: 'start'});
    return this.$webSocket.subscribe(observer);
  }

  ngOnDestroy() {
    this.$webSocket.complete();
    this.$webSocket.unsubscribe();
  }

  // stressTest(): void {
  //   let points: WavePoint[] = [];
  //   interval(20)
  //     .pipe(take(60), repeat())
  //     .subscribe((e) => {
  //       let wavePoint = this.createRandomWavePoint();
  //       if (e === 14) {
  //         wavePoint.z = -1;
  //       }
  //       wavePoint.index = points.length;
  //       points.push(wavePoint);
  //       if (e === 14) {
  //         this.storeWave(new Wave(this.system.currentUser.id).withPoints(points));
  //         points = [];
  //       }
  //     });
  // }

  // private createRandomWavePoint(): WavePoint {
  //   return new WavePoint(
  //     0,
  //     new Point(Math.random() * 100, Math.random() * 100, 0),
  //     0
  //   );
  // }
}
