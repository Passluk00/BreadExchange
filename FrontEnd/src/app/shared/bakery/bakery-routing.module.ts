import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BakeryComponent} from "./pages/bakery/bakery.component";

const routes: Routes = [
  {
    path:':id',
    component:BakeryComponent,
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BakeryRoutingModule { }
