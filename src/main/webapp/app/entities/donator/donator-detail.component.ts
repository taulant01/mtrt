import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonator } from 'app/shared/model/donator.model';

@Component({
  selector: 'jhi-donator-detail',
  templateUrl: './donator-detail.component.html',
})
export class DonatorDetailComponent implements OnInit {
  donator: IDonator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donator }) => (this.donator = donator));
  }

  previousState(): void {
    window.history.back();
  }
}
