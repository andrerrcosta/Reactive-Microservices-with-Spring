import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Observable } from 'rxjs';
import { GameAnalyticsModel } from '../models/game-analytics.model';
import { environment } from './../../environments/environment';
import { UserModel } from './../models/user.model';
import { Wave } from './../utils/wave.module';

@Injectable()
export class SystemService implements OnDestroy {

  currentUser: UserModel;

  constructor(private http: HttpClient) {
    this.currentUser = { id: '', email: '', name: '' };
  }

  storeWave(wave: Wave): Observable<any> {
    console.log("Storing", wave);
    return this.http.post(`${environment.svcWaveStream}/save`, wave);
  }

  getUser(): void {
    this.http.get<UserModel>(`${environment.svcUser}`).subscribe((user) => {
      this.currentUser = user;
      console.log('User Registered', this.currentUser);
    });
  }

  getUserAnalytics(): Observable<GameAnalyticsModel[]> {
    console.log('GetUserAnalytics', this.currentUser.id);

    return this.http.get<GameAnalyticsModel[]>(
      `${environment.svcAnalyticsClient}/user/${this.currentUser.id}`
    );
  }



  ngOnDestroy() {
    // this.currentUser = undefined;
  }

}
