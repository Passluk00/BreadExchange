import {ChangeDetectorRef, Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {isPlatformBrowser, NgForOf, NgIf} from "@angular/common";
import {AdminControllerService} from "../../../../../../services/services/admin-controller.service";
import {
  PageResponseBakeryRegisterRequest
} from "../../../../../../services/models/page-response-bakery-register-request";
import {UserControllerService} from "../../../../../../services/services/user-controller.service";
import {PageResponseUserResponse} from "../../../../../../services/models/page-response-user-response";

@Component({
  selector: 'app-list-users',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './list-users.component.html',
  standalone: true,
  styleUrl: './list-users.component.scss'
})
export class ListUsersComponent implements OnInit{


  constructor(
    private adminService: AdminControllerService,
    private userService: UserControllerService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}


  page= 0;
  size = 10;
  users: PageResponseUserResponse = {};
  pages: any =[]


  ngOnInit(): void {
    this.findAllUser()
    this.cdr.detectChanges()
  }


  findAllUser(){
    if(isPlatformBrowser(this.platformId)) {
      const toc = this.getToken()
      if (toc) {
        this.adminService.getAllUsers({
          page: this.page,
          size: this.size
        }).subscribe({
          next: (res) => {
            this.users = res;
            this.pages = Array(this.users.totalPages).fill(0).map((x,i) =>i )
          },
          error: (err) => {
            console.error("Fetch request Fallito: " + err)
          }
        })
      }
    }
  }

  goToPreviusPage(){
    this.page --;
    this.findAllUser()
  }

  goToPage(page: number){
    this.page = page;
    this.findAllUser()
  }

  goToNextPage(){
    this.page ++
    this.findAllUser()
  }

  isLastPage(){
    return this.page === this.users.totalPages as number - 1;
  }


  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token");
    }
    return null
  }

  ban(id:number){
    this.adminService.banUser({
      id:id
    }).subscribe({
      next: () => {
        console.log("Richiesta Rifiutata con Successo")
        this.cdr.detectChanges()
        window.location.reload()
      },
      error: () => {
        console.log("Richiesta Deny Fallita")
      }
    })
  }


  toSearch: string = "";
  isEmpty = true;

  searchRequestByName(){
    this.users = {}
    this.userService.searchUserByName({
      name: this.toSearch
    }).subscribe({
      next:(res) => {
        this.users = res
        this.isEmpty = false;
      },
      error:() => {
        this.isEmpty = true;
        console.error("Errore nella ricerca tramite nome")
      }
    })
  }










}
