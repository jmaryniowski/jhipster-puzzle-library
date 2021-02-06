import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PuzzlelibrarySharedModule } from 'app/shared/shared.module';

import { AuditsComponent } from './audits.component';

import { auditsRoute } from './audits.route';

@NgModule({
  imports: [PuzzlelibrarySharedModule, RouterModule.forChild([auditsRoute])],
  declarations: [AuditsComponent],
})
export class AuditsModule {}
