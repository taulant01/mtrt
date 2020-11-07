import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'treat',
        loadChildren: () => import('./treat/treat.module').then(m => m.MtrtTreatModule),
      },
      {
        path: 'image',
        loadChildren: () => import('./image/image.module').then(m => m.MtrtImageModule),
      },
      {
        path: 'donator',
        loadChildren: () => import('./donator/donator.module').then(m => m.MtrtDonatorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MtrtEntityModule {}
