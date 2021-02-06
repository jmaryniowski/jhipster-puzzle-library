import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PuzzlelibrarySharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [PuzzlelibrarySharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent],
})
export class LogsModule {}
