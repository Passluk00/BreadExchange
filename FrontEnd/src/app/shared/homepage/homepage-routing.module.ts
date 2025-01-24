import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomepageComponent} from "./pages/home/homepage.component";
import {UserComponent} from "./pages/user/user.component";
import {MainComponent} from "./pages/main/main.component";
import {AdminComponent} from "./pages/admin/admin.component";


const routes: Routes = [
  {
    path:"",
    component: HomepageComponent,
    children:[

      {
        path:"",
        component: MainComponent,
        title:"BreadExchange"
      },
      {
        path:'user',
        component:UserComponent,
        title:"User"
      },
      {
        path:'admin',
        component: AdminComponent,
        title: "Admin-Panel"
      },

    ]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomepageRoutingModule { }
