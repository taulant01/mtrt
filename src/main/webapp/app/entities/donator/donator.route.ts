import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDonator, Donator } from 'app/shared/model/donator.model';
import { DonatorService } from './donator.service';
import { DonatorComponent } from './donator.component';
import { DonatorDetailComponent } from './donator-detail.component';
import { DonatorUpdateComponent } from './donator-update.component';

@Injectable({ providedIn: 'root' })
export class DonatorResolve implements Resolve<IDonator> {
  constructor(private service: DonatorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDonator> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((donator: HttpResponse<Donator>) => {
          if (donator.body) {
            return of(donator.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Donator());
  }
}

export const donatorRoute: Routes = [
  {
    path: '',
    component: DonatorComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'mtrtApp.donator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DonatorDetailComponent,
    resolve: {
      donator: DonatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mtrtApp.donator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DonatorUpdateComponent,
    resolve: {
      donator: DonatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mtrtApp.donator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DonatorUpdateComponent,
    resolve: {
      donator: DonatorResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mtrtApp.donator.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
