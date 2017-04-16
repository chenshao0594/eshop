import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ShoppingCartItem } from './shopping-cart-item.model';
import { ShoppingCartItemService } from './shopping-cart-item.service';

@Component({
    selector: 'jhi-shopping-cart-item-detail',
    templateUrl: './shopping-cart-item-detail.component.html'
})
export class ShoppingCartItemDetailComponent implements OnInit, OnDestroy {

    shoppingCartItem: ShoppingCartItem;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private shoppingCartItemService: ShoppingCartItemService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['shoppingCartItem']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShoppingCartItems();
    }

    load(id) {
        this.shoppingCartItemService.find(id).subscribe((shoppingCartItem) => {
            this.shoppingCartItem = shoppingCartItem;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.shoppingCartItem.id !== undefined) {
            this.shoppingCartItemService.update(this.shoppingCartItem)
                .subscribe((res: ShoppingCartItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shoppingCartItemService.create(this.shoppingCartItem)
                .subscribe((res: ShoppingCartItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShoppingCartItem) {
        this.eventManager.broadcast({ name: 'shoppingCartItemModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShoppingCartItems() {
        this.eventSubscriber = this.eventManager.subscribe('shoppingCartItemListModification', (response) => this.load(this.shoppingCartItem.id));
    }
}
