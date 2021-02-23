import { AnalyticsComponent } from './components/analytics/analytics.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GameComponent } from './components/game/game.component';
import { StressComponent } from './components/stress/stress.component';
import { GameReportComponent } from './components/game-report/game-report.component';

const routes: Routes = [
  {
    path: '',
    component: GameComponent,
  },
  { path: 'analytics', component: AnalyticsComponent },
  { path: 'stress', component: StressComponent },
  { path: 'game-analysis', component: GameReportComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
