import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MtrtSharedModule } from 'app/shared/shared.module';
import { TreatComponent } from './treat.component';
import { TreatDetailComponent } from './treat-detail.component';
import { TreatUpdateComponent } from './treat-update.component';
import { TreatDeleteDialogComponent } from './treat-delete-dialog.component';
import { treatRoute } from './treat.route';

@NgModule({
  imports: [MtrtSharedModule, RouterModule.forChild(treatRoute)],
  declarations: [TreatComponent, TreatDetailComponent, TreatUpdateComponent, TreatDeleteDialogComponent],
  entryComponents: [TreatDeleteDialogComponent],
})
export class MtrtTreatModule {}
