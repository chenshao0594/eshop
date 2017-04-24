import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MerchantStoreComponent } from './merchant-store.component';
import { MerchantStoreDetailComponent } from './merchant-store-detail.component';
import { MerchantStorePopupComponent, MerchantStoreDialogComponent } from './merchant-store-dialog.component';
import { MerchantStoreDeletePopupComponent } from './merchant-store-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MerchantStoreResolvePagingParams implements Resolve<any> {

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

export const merchantStoreRoute: Routes = [
  {
    path: 'merchant-store',
    component: MerchantStoreComponent,
    resolve: {
      'pagingParams': MerchantStoreResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantStore.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'merchant-store/:id',
    component: MerchantStoreDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantStore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'merchant-store-new',
    component: MerchantStoreDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantStore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'merchant-store/:id/edit',
    component: MerchantStoreDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantStore.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const merchantStorePopupRoute: Routes = [
  
  
  {
    path: 'merchant-store/:id/delete',
    component: MerchantStoreDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.merchantStore.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
