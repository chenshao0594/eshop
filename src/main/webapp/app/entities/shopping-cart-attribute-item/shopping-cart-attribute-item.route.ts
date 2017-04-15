import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ShoppingCartAttributeItemComponent } from './shopping-cart-attribute-item.component';
import { ShoppingCartAttributeItemDetailComponent } from './shopping-cart-attribute-item-detail.component';
import { ShoppingCartAttributeItemPopupComponent, ShoppingCartAttributeItemDialogComponent } from './shopping-cart-attribute-item-dialog.component';
import { ShoppingCartAttributeItemDeletePopupComponent } from './shopping-cart-attribute-item-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ShoppingCartAttributeItemResolvePagingParams implements Resolve<any> {

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

export const shoppingCartAttributeItemRoute: Routes = [
  {
    path: 'shopping-cart-attribute-item',
    component: ShoppingCartAttributeItemComponent,
    resolve: {
      'pagingParams': ShoppingCartAttributeItemResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartAttributeItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'shopping-cart-attribute-item-new',
    component: ShoppingCartAttributeItemDialogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartAttributeItem.home.title'
    },
  }, {
    path: 'shopping-cart-attribute-item/:id',
    component: ShoppingCartAttributeItemDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartAttributeItem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shoppingCartAttributeItemPopupRoute: Routes = [
  {
    path: 'shopping-cart-attribute-item/:id/edit',
    component: ShoppingCartAttributeItemPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartAttributeItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'shopping-cart-attribute-item/:id/delete',
    component: ShoppingCartAttributeItemDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'eshopApp.shoppingCartAttributeItem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
