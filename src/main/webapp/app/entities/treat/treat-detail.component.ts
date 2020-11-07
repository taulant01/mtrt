import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITreat } from 'app/shared/model/treat.model';

@Component({
  selector: 'jhi-treat-detail',
  templateUrl: './treat-detail.component.html',
})
export class TreatDetailComponent implements OnInit {
  treat: ITreat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ treat }) => (this.treat = treat));
  }

  previousState(): void {
    window.history.back();
  }
}
