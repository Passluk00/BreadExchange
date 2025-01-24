import {ChangeDetectorRef, Component, Inject, OnInit, PLATFORM_ID} from '@angular/core';
import {AdminControllerService} from "../../../../../../services/services/admin-controller.service";
import {UserControllerService} from "../../../../../../services/services/user-controller.service";
import {PageResponseUserResponse} from "../../../../../../services/models/page-response-user-response";
import {isPlatformBrowser, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-list-user-click',
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './list-user-click.component.html',
  standalone: true,
  styleUrl: './list-user-click.component.scss'
})
export class ListUserClickComponent implements OnInit{

  constructor(
    private adminService: AdminControllerService,
    private userService: UserControllerService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}


  page= 0;
  size = 10;
  users: PageResponseUserResponse = {};
  test = this.users.content
  pages: any =[]


  ngOnInit(): void {
    this.findAllUser()
    this.cdr.detectChanges()
  }



  // TODO cambaire funzione get data


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

  getToken(): string | null{
    if(typeof localStorage.getItem('token') !== 'undefined' && typeof window !=='undefined'){
      return localStorage.getItem("token");
    }
    return null
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








}
