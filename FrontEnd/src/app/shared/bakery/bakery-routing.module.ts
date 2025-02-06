import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BakeryComponent} from "./pages/bakery/bakery.component";
import {MenuComponent} from "./pages/menu/menu.component";

const routes: Routes = [
  {
    path:':id',
    component:BakeryComponent,
  },
  {
    path:'menu/:id',
    component:MenuComponent,
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BakeryRoutingModule { }

