/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumDetailComponent } from 'app/entities/kriterijum/kriterijum-detail.component';
import { Kriterijum } from 'app/shared/model/kriterijum.model';

describe('Component Tests', () => {
    describe('Kriterijum Management Detail Component', () => {
        let comp: KriterijumDetailComponent;
        let fixture: ComponentFixture<KriterijumDetailComponent>;
        const route = ({ data: of({ kriterijum: new Kriterijum(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(KriterijumDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KriterijumDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.kriterijum).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
