import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { EmailSetting } from './email-setting.model';
import { EmailSettingService } from './email-setting.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-email-setting',
    templateUrl: './email-setting.component.html'
})
export class EmailSettingComponent implements OnInit, OnDestroy {
emailSettings: EmailSetting[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private emailSettingService: EmailSettingService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
        this.jhiLanguageService.setLocations(['emailSetting', 'sMTPSecurityEnum']);
    }

    loadAll() {
        if (this.currentSearch) {
            this.emailSettingService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.emailSettings = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.emailSettingService.query().subscribe(
            (res: Response) => {
                this.emailSettings = res.json();
                this.currentSearch = '';
            },
            (res: Response) => this.onError(res.json())
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEmailSettings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EmailSetting) {
        return item.id;
    }
    registerChangeInEmailSettings() {
        this.eventSubscriber = this.eventManager.subscribe('emailSettingListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
