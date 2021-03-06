import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransactionComponent } from './transaction.component';
import { TransactionDetailComponent } from './transaction-detail.component';
import { TransactionPopupComponent, TransactionDialogComponent } from './transaction-dialog.component';
import { TransactionDeletePopupComponent } from './transaction-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class TransactionResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const transactionRoute: Routes = [
  {
    path: 'transaction',
    component: TransactionComponent,
    resolve: {
      'pagingParams': TransactionResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.transaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'transaction/:id',
    component: TransactionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.transaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'transaction-new',
    component: TransactionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.transaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'transaction/:id/edit',
    component: TransactionDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.transaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionPopupRoute: Routes = [
  
  
  {
    path: 'transaction/:id/delete',
    component: TransactionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.transaction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
