import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BakeryComponent} from "./pages/bakery/bakery.component";
import {MenuComponent} from "./pages/menu/menu.component";
import {NewOrderComponent} from "./pages/new-order/new-order.component";
import {RiepilogoComponent} from "./pages/riepilogo/riepilogo.component";
import {PaymentComponent} from "./pages/payment/payment.component";
import {ManageComponent} from "./pages/manage/manage.component";

const routes: Routes = [
  {
    path:':id',
    component:BakeryComponent,
  },
  {
    path:'menu/:id',
    component:MenuComponent,
  },
  {
    path:"newOrder/:id",
    component: NewOrderComponent,
  },
  {
    path:'riepilogo/:id',
    component: RiepilogoComponent,
    title:"Riepilogo Ordine"
  },
  {
    path:'payment/:id',
    component:PaymentComponent,
    title:"Pagamento"
  },
  {
    path:'manage/:id',
    component: ManageComponent,
    title:"Manage"
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BakeryRoutingModule { }

