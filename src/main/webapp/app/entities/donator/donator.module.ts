import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MtrtSharedModule } from 'app/shared/shared.module';
import { DonatorComponent } from './donator.component';
import { DonatorDetailComponent } from './donator-detail.component';
import { DonatorUpdateComponent } from './donator-update.component';
import { DonatorDeleteDialogComponent } from './donator-delete-dialog.component';
import { donatorRoute } from './donator.route';

@NgModule({
  imports: [MtrtSharedModule, RouterModule.forChild(donatorRoute)],
  declarations: [DonatorComponent, DonatorDetailComponent, DonatorUpdateComponent, DonatorDeleteDialogComponent],
  entryComponents: [DonatorDeleteDialogComponent],
})
export class MtrtDonatorModule {}
