/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumDetailComponent } from 'app/entities/admin-kriterijum/admin-kriterijum-detail.component';
import { AdminKriterijum } from 'app/shared/model/admin-kriterijum.model';

describe('Component Tests', () => {
    describe('AdminKriterijum Management Detail Component', () => {
        let comp: AdminKriterijumDetailComponent;
        let fixture: ComponentFixture<AdminKriterijumDetailComponent>;
        const route = ({ data: of({ adminKriterijum: new AdminKriterijum(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdminKriterijumDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdminKriterijumDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adminKriterijum).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
