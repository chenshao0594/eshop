import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ShoppingCartItemComponent } from './shopping-cart-item.component';
import { ShoppingCartItemDetailComponent } from './shopping-cart-item-detail.component';
import { ShoppingCartItemPopupComponent, ShoppingCartItemDialogComponent } from './shopping-cart-item-dialog.component';
import { ShoppingCartItemDeletePopupComponent } from './shopping-cart-item-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ShoppingCartItemResolvePagingParams implements Resolve<any> {

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

export const shoppingCartItemRoute: Routes = [
  {
    path: 'shopping-cart-item',
    component: ShoppingCartItemComponent,
    resolve: {
      'pagingParams': ShoppingCartItemResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'shopping-cart-item-new',
    component: ShoppingCartItemDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartItem.home.title'
    },
  }, {
    path: 'shopping-cart-item/:id',
    component: ShoppingCartItemDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shoppingCartItemPopupRoute: Routes = [
  {
    path: 'shopping-cart-item/:id/edit',
    component: ShoppingCartItemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'shopping-cart-item/:id/delete',
    component: ShoppingCartItemDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
