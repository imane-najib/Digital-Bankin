import { Component, OnInit } from '@angular/core';

import { AsyncPipe, JsonPipe, NgForOf, NgIf } from '@angular/common';
import { CustomerService } from '../services/customer-service';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Customer } from '../Model/customer.model';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-customers',
  imports: [NgIf, NgForOf, AsyncPipe, ReactiveFormsModule],
  templateUrl: './customers.html',
  styleUrl: './customers.css',
})
export class Customers implements OnInit {
  customers!: Observable<Array<Customer>>;
  errorMessage!: string;
  searchFormGroup: FormGroup | undefined;

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.hundleSearchCustomers();
  }

  protected hundleSearchCustomers() {
      let kw = this.searchFormGroup?.value.keyword
      this.customers = this.customerService.searchCustomers(kw).pipe(
        catchError((err) => {
          this.errorMessage = err.message;
          return throwError(err);
        }),
      );
  }

  protected hundleDeleteCustomer(c:Customer) {
    let conf = confirm("Are you sure ?");
    if (!conf) return;

    this.customerService.DeleteCustomerID(c.id).subscribe({
      next: (resp)=> {
         this.customers = this.customers.pipe(
           map(data=>{
             let index = data.indexOf(c);
             data.slice(index,1);
             return data;
           })
         )
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
