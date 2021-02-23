import { getElement } from 'src/app/utils/dom.functions';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-analytics',
  templateUrl: 'analytics.component.html',
  styleUrls: ['./analytics.component.css'],
})
export class AnalyticsComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit() {}

  route(path: String) {
    getElement('analytics-container')?.classList.add('fade-to-dark-1s');
    let t = setTimeout(() => {
      this.router.navigate([path]);
    }, 1000);
  }
}
