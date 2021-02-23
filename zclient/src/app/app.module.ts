import { SystemStresserService } from './services/stresser.service';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AnalyticsComponent } from './components/analytics/analytics.component';
import { GameReportComponent } from './components/game-report/game-report.component';
import { GameComponent } from './components/game/game.component';
import { MenuComponent } from './components/menu/menu.component';
import { StressComponent } from './components/stress/stress.component';
import { SystemService } from './services/system.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    GameComponent,
    AnalyticsComponent,
    StressComponent,
    GameReportComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxChartsModule,
    BrowserAnimationsModule
  ],
  providers: [SystemService, SystemStresserService],
  bootstrap: [AppComponent],
})
export class AppModule {}
