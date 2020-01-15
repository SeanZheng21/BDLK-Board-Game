import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DiaballikComponent } from './diaballik/diaballik.component';
import { StarterComponent } from './starter/starter.component';

const routes: Routes = [{path: 'diaballik', component: DiaballikComponent},
{path: 'starter', component: StarterComponent},
{ path: '',
    redirectTo: '/starter',
    pathMatch: 'full'
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
